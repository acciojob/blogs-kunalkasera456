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
        Image image = imageRepository2.findById(id).get();
        imageRepository2.delete(image);

        //also delete from blog
        for(Blog blog : blogRepository2.findAll()) {
            if(blog.getImageList().contains(image)) {
                blog.getImageList().remove(image);
                blogRepository2.save(blog);
                break;
            }
        }
    }

    public int countImagesInScreen(Integer id, String screenDimensions) {
        //Find the number of images of given dimensions that can fit in a screen having `screenDimensions`

        int count=0;
        Image image=imageRepository2.findById(id).get();
        image.getDimensions();
        String bigSreen[]=screenDimensions.split("X");
        int x=Integer.parseInt(bigSreen[0]);
        int y=Integer.parseInt(bigSreen[1]);
        int screenArea=x*y;

        String imageSize[]=image.getDimensions().split("X");
        int m=Integer.parseInt(imageSize[0]);
        int n=Integer.parseInt(imageSize[1]);
        int imageArea=m*n;

        return screenArea/imageArea;

    }
}
