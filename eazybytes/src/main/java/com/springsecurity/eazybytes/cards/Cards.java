package com.springsecurity.eazybytes.cards;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "cards")
public class Cards {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long cardId;

	private Long customerId;

	private String cardNumber;

	private String cardType;

	private Integer totalLimit;

	private Integer amountUsed;

	private Integer availableAmount;

	@CreationTimestamp
	private LocalDateTime createDt;

}
