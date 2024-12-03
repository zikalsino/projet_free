package com.example.demo.Handlers;


import lombok.Data;

@Data
public class JsonViewModel {
    private String message;



    public static JsonViewModelBuilder builder() {
        return new JsonViewModelBuilder();
    }

    public static class JsonViewModelBuilder {
        private String message;

        public JsonViewModelBuilder message(String message) {
            this.message = message;
            return this;
        }

        public JsonViewModel build() {
            JsonViewModel viewModel = new JsonViewModel();
            viewModel.setMessage(this.message);
            return viewModel;
        }
    }
}
