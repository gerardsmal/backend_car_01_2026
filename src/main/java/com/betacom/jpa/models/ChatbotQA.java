package com.betacom.jpa.models;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.hibernate.annotations.Array;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import com.betacom.jpa.enums.ChatRoleContext;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "chatbot_qa")
public class ChatbotQA {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(unique = true, length = 100)
	private String code;

	@Column(nullable = false, columnDefinition = "TEXT")
	private String question;

	@Column(nullable = false, columnDefinition = "TEXT")
	private String answer;

	@Column(length = 100)
	private String category;

	@Enumerated(EnumType.STRING)               // transform la enum in string in db
	@Column(nullable = false, length = 50)
	private ChatRoleContext targetRole;

	@Column(nullable = false)
	private Integer weight = 1;

	@Column(nullable = false)
	private Boolean active = true;

	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	
	@JdbcTypeCode(SqlTypes.VECTOR)
	@Array(length = 1536)
	@Column(name = "embedding", columnDefinition = "vector(1536)")
	private float[] embedding;
	
	@PrePersist                               // da eseguire prima della persist
	void onCreate() {
		createdAt = LocalDateTime.now();
		updatedAt = LocalDateTime.now();
	}

	@PreUpdate                                // da eseguire prima dell'update
	void onUpdate() {
		updatedAt = LocalDateTime.now();
	}
}
