package com.example.serly.ghtoll.models;

public class PaymentClass {


        private float price;
        private String network;
        private String recipient_number;
        private String sender;
        private String option;
        private String apikey;
        private String orderID;

    public PaymentClass() {
    }

    public PaymentClass(float price, String network, String recipient_number, String sender, String option, String apikey, String orderID) {
        this.price = price;
        this.network = network;
        this.recipient_number = recipient_number;
        this.sender = sender;
        this.option = option;
        this.apikey = apikey;
        this.orderID = orderID;
    }

// Getter Methods


        public float getPrice() {
            return price;
        }

        public String getNetwork() {
            return network;
        }

        public String getRecipient_number() {
            return recipient_number;
        }

        public String getSender() {
            return sender;
        }

        public String getOption() {
            return option;
        }

        public String getApikey() {
            return apikey;
        }

        public String getOrderID() {
            return orderID;
        }

        // Setter Methods

        public void setPrice(float price) {
            this.price = price;
        }

        public void setNetwork(String network) {
            this.network = network;
        }

        public void setRecipient_number(String recipient_number) {
            this.recipient_number = recipient_number;
        }

        public void setSender(String sender) {
            this.sender = sender;
        }

        public void setOption(String option) {
            this.option = option;
        }

        public void setApikey(String apikey) {
            this.apikey = apikey;
        }

        public void setOrderID(String orderID) {
            this.orderID = orderID;
        }

}
