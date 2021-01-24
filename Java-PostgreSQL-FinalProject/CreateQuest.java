/*
 * Name: Satinder Sikand
 * Date: December 13, 2020
 * Student#: 215558661
 * Project: App
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

import pgpass.PgPass;
import pgpass.PgPassException;

public class CreateQuest {

	//Database variables
    private Connection conDB;        // Connection to the database system.
    private String url;              // URL: Which database?
    private String user = ""; // Database user account
    
    //Query variable
    private String tquery;
    
    //Relation variables
    private String realm, theme;
    private Date date;
    private int amount;
    private float seed;
  
    public CreateQuest(String [] args) {
    	//Convert arguements
        this.convertArgs(args);
    	
    	// Set up the DB connection.
        try {
            // Register the driver with DriverManager.
            Class.forName("org.postgresql.Driver").newInstance();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.exit(0);
        } catch (InstantiationException e) {
            e.printStackTrace();
            System.exit(0);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            System.exit(0);
        }

        // URL: Which database?
        //url = "jdbc:postgresql://db:5432/<dbname>?currentSchema=yrb";
        url = "jdbc:postgresql://db:5432/";

        // set up acct info
        // fetch the PASSWD from <.pgpass>
        Properties props = new Properties();
        try {
            String passwd = PgPass.get("db", "*", user, user);
            props.setProperty("user",    user);
//            System.out.println(passwd);
            props.setProperty("password", passwd);
            // props.setProperty("ssl","true"); // NOT SUPPORTED on DB
        } catch(PgPassException e) {
            System.out.print("\nCould not obtain PASSWD from <.pgpass>.\n");
            System.out.println(e.toString());
            System.exit(0);
        }

        // Initialize the connection.
        try {
            // Connect with a fall-thru id & password
            //conDB = DriverManager.getConnection(url,"<username>","<password>");
            conDB = DriverManager.getConnection(url, props);
        } catch(SQLException e) {
            System.out.print("\nSQL: database connection error.\n");
            System.out.println(e.toString());
            System.exit(0);
        }    

        try {
            conDB.setAutoCommit(true);
        } catch(SQLException e) {
            System.out.print("\nFailed trying to turn autocommit off.\n");
            e.printStackTrace();
            System.exit(0);
        } 
        
        //Done checking Database connectivity
        //Check if realm and amount are valid
        correctRealm(this.realm);
        checkAmount(this.amount);
        if (this.seed < -1 || this.seed > 1) {
        	System.out.println("Seed value is improper");
        	System.exit(0);
        }
        
        //Add the quest and loot
        addQuest();
        addTreasureLoot();
        
     // Close the connection.
        try {
            conDB.close();
        } catch(SQLException e) {
            System.out.print("\nFailed trying to close the connection.\n");
            e.printStackTrace();
            System.exit(0);
        } 
    }
    
    private void addQuest() {
    	//Set the sql statement
    	String queryt = "";
		PreparedStatement qst = null;
		int result;
		
		//Set query text
		queryt = "insert into quest(theme, realm, day, succeeded) values " + "(?, ?, ?, null)";
		qst = prepareQuery(queryt);
		
		//Try executing the query
		try {
			//Set theme and realm
			qst.setString(1, this.theme);
			qst.setString(2, this.realm);
			
			//Create an sql date
			java.sql.Date tempdate = new java.sql.Date(this.date.getTime());
			qst.setDate(3, tempdate);
			System.out.println(this.date);
			System.out.println(qst);
			result = qst.executeUpdate();
		} catch (SQLException e) {
			System.out.println("SQL#1 failed in execute for addQuest");
			System.out.println(e.toString());
			System.exit(0);
		}
		
		closeqc(qst, null);
    }
    
    private void addTreasureLoot() {
    	//Set the sql statement
    	String queryt = "";
		PreparedStatement qst = null;
		ResultSet result = null;
		int totalAmount = 0;
		
		//Do seeding
		queryt = "select setseed ("+this.seed+")";//?) ";
		qst = prepareQuery(queryt);

		try {
			//qst.setFloat(1, this.seed);
			System.out.println("Seed value: " + seed);
			qst.executeQuery();
		} catch (SQLException e) {
			System.out.println("SQL#1 failed in execute for addTreasureLoot");
			System.out.println(e.toString());
			System.exit(0);
		}
		
		queryt = " select * from Treasure order by random() ";
		qst = prepareQuery(queryt);
		try {
			result = qst.executeQuery();
		} catch (SQLException e) {
			System.out.println("SQL#2 failed in execute for addTreasureLoot");
			System.out.println(e.toString());
			System.exit(0);
		}
		
		//update loot
		String lootquery = " insert into Loot(loot_id, treasure, theme, realm, day ) values "
				+ " (?, ?, ?, ?, ?) ";
		PreparedStatement lqst = prepareQuery(lootquery);
		int lootID = 1;
		try {
			while (this.amount > totalAmount) {
				result.next();
				
				//Set query;
				lqst.setInt(1, lootID);
				lqst.setString(2, result.getString("treasure"));
				lqst.setString(3, this.theme);
				lqst.setString(4, this.realm);
				
				java.sql.Date tempDate = new java.sql.Date(this.date.getTime());
				lqst.setDate(5, tempDate);
				//execute update
				lqst.executeUpdate();
				lootID++;
				totalAmount += result.getInt("sql");
			}
		} catch (SQLException e) {
			System.out.println("SQL#3 failed in execute for addTreasureLoot num#: " + lootID);
			System.out.println(e.toString());
			System.exit(0);
		}
		
		closeqc(qst, null);
		closeqc(lqst, result);
    }
    
    public void convertArgs(String [] args) {
    	if (args.length < 4) { 
        	System.out.println("\nThere are not enough arguements/parameters provided.\n" 
        			+ "Format: CreateQuest <day> <realm> <theme> <amount> [<user>] [seed]");
        	System.exit(0);
        } else {
    		//Set String variables
    		 String [] datearr = args[0].split("-");
    		//Get actual date from here
    		//Date format is yyyy-mm-dd
    		int y = Integer.parseInt(datearr[0]), m = Integer.parseInt(datearr[1]), d = Integer.parseInt(datearr[2]);
    		Calendar inputdate = Calendar.getInstance();
    		inputdate.set(y, m, d);
    		System.out.println(inputdate.getTime());

    		//Check if entered date is valid
    		if (inputdate.getTime().after(Calendar.getInstance().getTime())) {
    			try {
					this.date = new SimpleDateFormat("yyyy-MM-dd").parse(args[0]);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					System.out.println("Date is not in proper format (yyyy-mm-dd)");
					e.printStackTrace();
				}
    		} else {
    			System.out.println("The entered date is not set to one that is in the future.");
    			System.exit(0);
    		}
    		
    		this.realm = args[1];
    		this.theme = args[2];
    		
    		//Set other variables
    		this.amount = Integer.parseInt(args[3]);        		
    		if (args.length > 4)
    			this.user = args[4];
    		if (args.length == 6)
    			this.seed = Float.parseFloat(args[5]);
        }
    }
    
	private boolean correctRealm(String realm) {
		boolean valid = true;
		//Set the sql statement
		String queryt = "";
		PreparedStatement qst = null;
		ResultSet result = null;
		
		//Set query text
		queryt = "select * from realm " + " where realm = ?  ";
		qst = prepareQuery(queryt); 
		
		//Execute query
		try {
			qst.setString(1, this.realm);
			result = qst.executeQuery();
		} catch (SQLException e) {
			System.out.println("SQL#1 failed in execute for correctRealm");
			System.out.println(e.toString());
			System.exit(0);
		}
		
		//Check answers
		try {
			if(!result.isBeforeFirst()) {
				System.out.println("\nrealm does not exist");
				System.exit(0);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("SQL#1 failed in cursor for correct Realm");
            System.out.println(e.toString());
            System.exit(0);
		}
		
		closeqc(qst, result);
		return valid;
	}
	
	private boolean checkAmount(int amount) {
		boolean valid = true;
		
		//Set the sql statement
		String queryt = "";
		PreparedStatement qst = null;
		ResultSet result = null;
		
		//Set query text
		queryt = "select sum(sql) from treasure ";
		qst = prepareQuery(queryt);
		
		//Execute query
		try {
			result = qst.executeQuery();
		} catch (SQLException e) {
			System.out.println("SQL#1 failed in execute for checkAmount");
			System.out.println(e.toString());
			System.exit(0);
		}
		
		//Check answers
		try {
			if(!result.isBeforeFirst()) {
				System.out.println("amount does not exist");
				System.exit(0);
			} else {
				result.next();
				if (result.getInt("sum") < amount) {
					System.out.println("amount exceeds what is possible");
					System.exit(0);
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("SQL#1 failed in cursor for check amount");
            System.out.println(e.toString());
            System.exit(0);
		}
				
		closeqc(qst, result);
		return valid;
	}
	
	private PreparedStatement prepareQuery(String query) {
		PreparedStatement ps = null;
		try {
			ps = conDB.prepareStatement(query);
		} catch (SQLException e) {
			System.out.println("SQL#1 failed in prepare");
			System.out.println(e.toString());
			System.exit(0);
		}
		return ps;
	}
    
	public void closeqc(PreparedStatement qst, ResultSet result) {
		//Close prepared statement
		try {
			qst.close();
		} catch (SQLException e) {
			System.out.print("SQL#1 failed closing the handle.");
            System.out.println(e.toString());
            System.exit(0);
		}
		//Close the cursor
		try {
			if (result!=null)
				result.close();
		} catch (SQLException e) {
			System.out.println("SQL#1 failed closing cursor.");
			System.out.println(e.toString());
			System.exit(0);
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		CreateQuest cq = new CreateQuest(args);

	}
	
	

}
