package com.restapi.blog.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="post")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Post {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
   private Integer postId;
	
   private String title;
   
   private String content;
   
   private String imageName;
   
   private Date addedDate;
   
   
   @ManyToOne
   @JoinColumn(name = "category_id")
   private Category category;
   
   @ManyToOne
   private User user;
   
   @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
   private Set<Comment> comments =  new HashSet<>();
   
   
   
}
