package com.dayuanit.dto;

public class AjaxResultDTO {

		private boolean success;
		private String message;
		private Object data;
		
		private   AjaxResultDTO(boolean success, String message, Object data){
			 this.success=success;
			 this.message=message;
			 this.data=data;
		}
		
		
		
		public static  AjaxResultDTO failed(String errorMsg){
			
			return new AjaxResultDTO(false,errorMsg,null);
		}
		
	public static  AjaxResultDTO success(String successMsg){
			
			return new AjaxResultDTO(true,successMsg,null);
		}
	
	public static  AjaxResultDTO success( ){
		
		return new AjaxResultDTO(true,null,null);
	}
		
	public static AjaxResultDTO success(Object data) {
		return new AjaxResultDTO(true, null, data);
	}
		
		public boolean isSuccess() {
			return success;
		}
		public void setSuccess(boolean success) {
			this.success = success;
		}
		public String getMessage() {
			return message;
		}
		public void setMessage(String message) {
			this.message = message;
		}
		public Object getData() {
			return data;
		}
		public void setData(Object data) {
			this.data = data;
		}
}
