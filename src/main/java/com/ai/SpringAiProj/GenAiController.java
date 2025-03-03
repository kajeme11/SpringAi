package com.ai.SpringAiProj;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.ai.image.ImageResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class GenAiController {

    private final ChatService chatService;
    private final ImageService imageService;
    private final RecipeService recipeService;

    public GenAiController(ChatService chatService,
                           ImageService imageService,
                           RecipeService recipeService){

        this.chatService = chatService;
        this.imageService = imageService;
        this.recipeService = recipeService;

    }

    @GetMapping("ask-ai")
    public String getResponse(@RequestParam String prompt){
        return chatService.getResponse(prompt);
    }

    @GetMapping("ask-ai-options")
    public String getResponseOptions(@RequestParam String prompt){
        return chatService.getResponseOptions(prompt);
    }

//    redirects to 1 single image
//    @GetMapping("ask-ai-image")
//    public void generateImage(HttpServletResponse httpServletResponse,  @RequestParam String prompt) throws IOException {
//        ImageResponse imageResponse = imageService.generateImage(prompt);
//        String imageUrl =  imageResponse.getResult().getOutput().getUrl();
//        httpServletResponse.sendRedirect(imageUrl);
//    }
//http://localhost:8080/ask-ai-images?prompt=rockstar%20cat
    @GetMapping("ask-ai-images")
    public List<String> generateImages(HttpServletResponse httpServletResponse,  @RequestParam String prompt) throws IOException {
        ImageResponse imageResponse = imageService.generateImage(prompt);
        List<String> imageUrls =  imageResponse.getResults()
                .stream().map(result -> result.getOutput().getUrl()).toList();
        return imageUrls;

    }

//    http://localhost:8080/ai-recipe?ingredients=chicken, carrots, pototoes, jalapenos&cuisine="mexican"&restrictions="seafood allergy"
    @GetMapping("ai-recipe")
    public String generateImage(@RequestParam String ingredients,
                                @RequestParam(defaultValue = "any") String cuisine,
                                @RequestParam(defaultValue = "") String restrictions){

        return recipeService.createRecipe(ingredients, cuisine, restrictions);
    }
}
