package com.ai.SpringAiProj;

import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class RecipeService {
    private ChatModel chatModel;

    public RecipeService(ChatModel chatModel){
        this.chatModel = chatModel;
    }

    public String createRecipe(String ingredients, String cuisine, String dietaryRestrictions){
        var template = """
                Create a recipe using {ingredients}.
                The cuisine i prefer is {cuisine}.
                Consider the following dietary restrictions:{dietaryRestrictions},
                Provide detailed instructions with title, steps, ingredient quantities, 
                and cooking instructions.
                """;
        PromptTemplate promptTemplate = new PromptTemplate(template);
        Map<String, Object> params = Map.of(
                "ingredients", ingredients,
                "cuisine", cuisine,
                "dietaryRestrictions", dietaryRestrictions
        );

        Prompt prompt = promptTemplate.create(params);
        return chatModel.call(prompt).getResult().getOutput().getContent();
    }
}
