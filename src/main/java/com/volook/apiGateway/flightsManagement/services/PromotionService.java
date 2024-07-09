package com.volook.apiGateway.flightsManagement.services;

import org.springframework.stereotype.Service;

import com.volook.apiGateway.Microservice;

import flightsManager.Flights.PaginateQuery;
import flightsManager.Flights.PaginatedPromotions;
import flightsManager.Flights.Promotion;
import flightsManager.Flights.PromotionId;
import flightsManager.PromotionServiceGrpc.PromotionServiceBlockingStub;
import net.devh.boot.grpc.client.inject.GrpcClient;

@Service
public class PromotionService {
	
	@GrpcClient(Microservice.FLIGHTS_MANAGER)
	private PromotionServiceBlockingStub promotionServiceStub;
	
	public Promotion saveOrUpdate(Promotion promotion) {
		if(promotion==null) {
			return null;
		}
		Promotion savedPromotion = this.promotionServiceStub.saveOrUpdate(promotion);
		return savedPromotion;
	}
	
	public Promotion delete(String promotionId) {
		if(promotionId==null) {
			return null;
		}
		PromotionId id = PromotionId.newBuilder()
				.setId(promotionId)
				.build();
		Promotion deletedPromotion = this.promotionServiceStub.delete(id);
		return deletedPromotion;
	}
	
	public Promotion findOne(String promotionId) {
		if(promotionId==null) {
			return null;
		}
		PromotionId id = PromotionId.newBuilder()
				.setId(promotionId)
				.build();
		Promotion promotion = this.promotionServiceStub.findOne(id);
		return promotion;
	}
	
	public PaginatedPromotions find(String query) {
		if(query==null) {
			return null;
		}
		PaginateQuery paginateQuery = PaginateQuery.newBuilder()
				.setQuery(query)
				.build();
		PaginatedPromotions promotions = this.promotionServiceStub.find(paginateQuery);
		return promotions;
	}
	
}
