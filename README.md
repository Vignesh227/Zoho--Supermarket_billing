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

![image](https://github.com/user-attachments/assets/86f90f88-4507-4b59-ac67-e24ea63ced0c)
![image](https://github.com/user-attachments/assets/53a0e8cf-d805-40fb-8d6f-fb45a56994db)
![image](https://github.com/user-attachments/assets/daa87a1e-f923-4421-8d61-35e503fed011)
![image](https://github.com/user-attachments/assets/508cc347-f062-4f84-8a50-c015aaa36859)
![image](https://github.com/user-attachments/assets/45973149-1e65-4588-a77f-52a0f12ddd13)
![image](https://github.com/user-attachments/assets/ddb48b0b-a869-4f5d-9385-44d7133d593a)
![image](https://github.com/user-attachments/assets/b5c7a513-af66-487d-bea4-b5134c314c2f)







