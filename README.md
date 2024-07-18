# Zoho--Supermarket_billing

## Question / Challlenge
Create a Super Market Billing Console Application using `Java`, `JDBC`, and `MySQL` that handles multiple customers and products, generates bills for purchases, creates or logs in customer profiles, and updates customer profiles and product information after purchases.

## Solution Overview
The Supermarket Billing Application is designed to manage 
 - Customer Billing
 - Tracking Products
 - Customer Information
 - Billing Details.

## MySQL Database - Table Structure
![image](https://github.com/user-attachments/assets/41f7f699-9c2f-4c60-a1a8-30c19f8b4c07)

## Modules
- Billing Section
- Display All Bills of a Particular Customer
- Display Product Availability
- Display All Bills on a Particular Date
- Insert / Update Product

## Module Description
### 1) Billing Section

_Purpose:_
- This module manages the interaction with customers during the billing process. <br>

_Functionality:_
- Prompts the user to either log into an existing account or create a new account if the user is new.
- Allows the customer to select products and specify quantities.
- Generates a bill based on the selected products and quantities and displays the final bill to the customer.

### 2) Display All Bills of a Particular Customer

_Purpose:_ 
- This module retrieves and displays the billing history of a specific customer. <br>

_Functionality:_
- Prompts for the customer's credentials.
- Fetches and displays all past bills associated with the customer from the database.

### 3) Display Product Availability

_Purpose:_
- This module provides the DB admin with information about the current stock of products. <br>

_Functionality:_
- Shows the list of products available in the store, along with their remaining quantities and prices.
  
### 4) Display All Bills on a Particular Date

_Purpose:_ 
- This module retrieves and displays all bills generated on a specific date. <br>

_Functionality:_
- Prompts for a specific date.
- Fetches and displays all bills generated on that date from the database.
  
### 5) Insert / Update Product

_Purpose:_
- This module allows the DB admin to manage the products in the store. <br>

_Functionality:_
- Allows the admin to add new products to the 'Product' table.
- Allows the admin to update details of existing products in the 'Product' table, such as price and quantity.

## Ouput Screenshots

