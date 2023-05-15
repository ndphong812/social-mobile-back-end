package com.backend.social.entity;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Users implements Serializable {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="email",unique = true)
	@Email(message = "the email must be in correct format")
	@NotBlank
	private String email;
	
	@Column(name="phone",unique = true)
	@NotBlank(message = "phone must not be blank")
	private String phone;

	@Column(name="password")
	@Size(min = 6,message = "password must be greater than 6 char")
	@JsonIgnore
	private String password;
	
	@Column(name="full_name")
	@NotBlank(message="fullName must not be blank")
	@Size(max=100,message="The greatest length of the fullName is 100")
	private String fullName;
	
	@Column(name="nick_name")
	private String nickName;
	
	@Column(name="avatar")
	@Size(max = 200)
	private String avatar;
	
	@Column(name="dob")
	@Past
	@NotNull(message = "date of birth must not be null")
	private Date dob;
	
	@Column(name="created_at")
	@JsonIgnore
	private Timestamp createdAt;

	@Column(name = "locked")
	@JsonIgnore
	private Boolean locked;

	@Column(name = "enable")
	@JsonIgnore
	private Boolean enable;
	
	@Column(name="is_online")
	private Boolean isOnline;
	
	@Column(name="role")
	@NotBlank
	private String role;

}
