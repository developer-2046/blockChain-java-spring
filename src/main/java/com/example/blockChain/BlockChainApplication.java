package com.example.blockChain;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BlockChainApplication {

	public static void main(String[] args) {

		SpringApplication.run(BlockChainApplication.class, args);

	}
	
	@Bean
	public BlockChain blockChain() {
		return new BlockChain();
	}


}
