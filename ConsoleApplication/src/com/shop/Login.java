/*
 * Application title : Online Footwear Shopping system
 * Author            : F.Thahir Hussain
 * Created on        : April 9 2022
 * Last Modified date: April 18 2022
 * Reviewed by       :
 * Suggestions       :
 */
package com.shop;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
//It is used to perform the Login operations
class Login {
	private String username = "";
	private String password = "";
	private String user;//which type of user whether 'customer' , 'seller' and 'admin'
	private int count=0;
	private int set;//which is used to run the infinite loop till username or password was entered in correct format
	Accounts account;//It is used to create account object for managing all accounts.
    
    //It is used to check password with corresponding pattern.
	public static boolean isValid(String regex, String credential) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(credential);
		return matcher.matches();
	}
    //It is used to check the user whether new user or already existing user.
	public boolean checknewOrAlreadyExistingUser(String user, String username) {
		boolean customer = user.equals("customer") && account.customersMap.containsKey(username);
		boolean seller = user.equals("seller") && account.sellersMap.containsKey(username);
		boolean admin = user.equals("admin") && account.adminMap.containsKey(username);
		if (customer || seller || admin) {
			return true;
		}
		return false;
	}
    //It is used to add username and password to corresponding objects(Customer, Seller and Admin) 
	public void addToSystem(String user, String username, String password) {
		if (user.equals("customer")) {
			Customer customer = new Customer();
			customer.setUserName(username);
			customer.setPassword(password);
			account.addCustomerToSystem(customer);
		} else if (user.equals("seller")) {
			Seller seller = new Seller();
			seller.setUserName(username);
			seller.setPassword(password);
			account.addSellerToSystem(seller);
		} else if (user.equals("admin")) {
			Admin admin = new Admin();
			admin.setUsername(username);
			admin.setPassword(password);
			account.addAdminToSystem(admin);
		}
		System.out.println("Account was created successfully!!!");
	}
    //This constructor is invoked whenever login operation is performed.
	public Login(String user,Accounts account) {
		this.account=account;
		account.test();//for testing purpose.
		this.user = user;
		Scanner Scanner = new Scanner(System.in);
		boolean exit = true;
		do {
			System.out.println("-------------------");
			System.out.println("1.New Login\n2.Login\n3.Exit");
			System.out.println("--------------------");
			int choice;
			System.out.println("Enter the choice: ");
			choice = Scanner.nextInt();
			switch (choice) {
			case 1:
				set = 1;
				while (set == 1) {
					System.out.println("Enter the username: ");
					username = Scanner.next();
					String regex = "^(?=.*[0-9])" + "(?=.*[a-z])" + "(?=.*[A-Z]).{5,8}$";
					if (isValid(regex, username)) {
						if (checknewOrAlreadyExistingUser(user, username)) {
							System.out.println("Already exists!");
							continue;
						} else {
							set = 0;
							break;
						}
					} else {
						System.out.println("Invalid username format!.Try Again");
					}

				}
				while (set == 0) {
					System.out.println(
							"Rules for Valid Password\n1.It contains at least 8 characters and at most 20 characters.\n2.It contains at least one digit,one lowercase alphabet,one upper case alphabet and one special character.\n3.It does not contain whitespace.");
					System.out.println("\nEnter the password: ");
					password = Scanner.next();
					String regex = "^(?=.*[0-9])" + "(?=.*[a-z])(?=.*[A-Z])" + "(?=.*[@#$%^&+=])" + "(?=\\S+$).{8,20}$";
					if (!isValid(regex, password)) {
						System.out.println("Invalid Password format!.Try Again");
						continue;
					}
					set = 1;
				}
				this.addToSystem(user, username, password);
				break;
			case 2:
				set = 1;
				while (set == 1) {
					System.out.println("Username: ");
					username = Scanner.next();
					if (!this.checknewOrAlreadyExistingUser(user, username)) {
						System.out.println("Invalid User!");
						continue;
					}
					set = 0;
				}
				while (set == 0) {
					System.out.println("Password: ");
					password = Scanner.next();
					if (account.checkValidUser(user, username, password)) {
						System.out.println("Login successfully!");
						Home home=new Home(user,username,account);
						
						set = 1;
						break;
					}
					System.out.println("Invalid password!.Try Again!");
				}
				break;
			case 3:
				exit=false;
				break;
			}
		} while (exit);

	}
    //It is used to get the username from object(Customer, Seller and Admin)
	public String getUsername() {
		return username;
	}
	//It is used to set the  username from object(Customer, Seller and Admin)
	public void setUsername(String username) {
		this.username = username;
	}
    //It is used to get the password from object(Customer, Seller and Admin)
	public String getPassword() {
		return password;
	}
	 //It is used to set the password from object(Customer, Seller and Admin)
	public void setPassword(String password) {
		this.password = password;
	}

}