package com.driver.services;

import com.driver.models.*;
import com.driver.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImageService {

    @Autowired
    BlogRepository blogRepository2;
    @Autowired
    ImageRepository imageRepository2;

    public Image addImage(Integer blogId, String description, String dimensions){
        //add an image to the blog
        Image image = new Image();
        image.setDescription(description);
        image.setDimensions(dimensions);

        Blog blog = blogRepository2.findById(blogId).get();
        image.setBlog(blog);

        //also add image in blog image list
        List<Image> imageList = blog.getImageList();
        imageList.add(image);

        //set imageList
        blog.setImageList(imageList);

        blogRepository2.save(blog);

        return  image;

    }

    public void deleteImage(Integer id){
//        Image image = imageRepository2.findById(id).get();
//        imageRepository2.delete(image);
//
//        //also delete from blog
//        for(Blog blog : blogRepository2.findAll()) {
//            if(blog.getImageList().contains(image)) {
//                blog.getImageList().remove(image);
//                blogRepository2.save(blog);
//                break;
//            }
//        }
        imageRepository2.deleteById(id);
    }

    public int countImagesInScreen(Integer id, String screenDimensions) {
        //Find the number of images of given dimensions that can fit in a screen having `screenDimensions`

        Image image = imageRepository2.findById(id).get();
        String availableDimension = image.getDimensions();
        String[] arr = availableDimension.split("X");
        String[] brr = screenDimensions.split("X");

        int screenWidth = Integer.parseInt(brr[0]);
        int screenHeight = Integer.parseInt(brr[1]);
        int imageWidth = Integer.parseInt(arr[0]);
        int imageHeight = Integer.parseInt(arr[1]);

        int a = screenHeight/imageHeight;
        int b = screenWidth/imageWidth;

        return a*b;

    }
}
