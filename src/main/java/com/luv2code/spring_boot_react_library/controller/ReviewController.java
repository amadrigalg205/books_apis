package com.luv2code.spring_boot_react_library.controller;

import com.luv2code.spring_boot_react_library.requestmodels.ReviewRequest;
import com.luv2code.spring_boot_react_library.service.ReviewService;
import com.luv2code.spring_boot_react_library.utils.ExtractJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

//@CrossOrigin("https://luvtoread.vercel.app")
@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping("/secure/user/book")
    public Boolean reviewBookByUser(@RequestHeader(value="Authorization") String token,
                                    @RequestParam Long bookId) throws Exception {
        String userEmail = ExtractJWT.payloadJWTExtraction(token, "\"sub\"");

        if (userEmail == null) {
            throw new Exception("User email is missing");
        }
        return reviewService.userReviewListed(userEmail, bookId);
    }

    @PostMapping("/secure")
    public void postReview(@RequestHeader(value = "Authorization") String token,
                           @RequestBody ReviewRequest reviewRequest) throws Exception {
        String userMail = ExtractJWT.payloadJWTExtraction(token, "\"sub\"");
        if(userMail == null) {
            throw new Exception("User mail is missing");
        }
        reviewService.postReview(userMail, reviewRequest);
    }


}
