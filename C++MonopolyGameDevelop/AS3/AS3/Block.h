#pragma once// Conditional compilation
#include "Player.h"
#include <iostream>

//#ifndef _Block		// Conditional compilation
//#define _Block

class Block
{
public:
	//======================================================================
	//Basic function
	Block::Block();				//defult constructor
	Block(int a);			    //genarate new block with serial number 
	~Block();                   //Defult distructor

	//======================================================================
	//Get value function
	int getPrice();				//get the price of block
	int getInvest();			//get the investment situation
	bool getPro();				//get the property of the block
	std::string getowner();	    //return owner

	//======================================================================
	//Oprational function
	void setStartPoint(bool a); //set property of starting point
	void setStartPoint();       //set the start point property to 1
	void bonuss(Player p);      //give player bonus when pass the starting point
	void buy(Player a);			//set new owner of the block
	void investigate(Player a); //investigate the block
	bool investJudge();			//judge whether the location can be invest because the upper limit of investment
	void changeOwner(std::string a);//change the owner when buy the block
	int getblocknumber();		//get the block number

	//======================================================================
	//function used to load game
	void loadBlock(int bn, int pr, int in, bool pro, std::string o);//used by loading module
	void setBlocknumber(int a); //set the block number of this block
	void setPrice(int a);		//set the price of this block
	void setInvestment(int a);  //set the investment level of this block
	void setProperty(bool a);   //set the property of this block
	void setOwner(std::string a);//set the owner of this block

private:
	int blocknumber;			//save the number of block
	int price;					//save the price of block
	int investment;			    // save the investment time
	bool property;				//property to distinguish start block(with out price) and normal block
	int priceRrange = 500;		// set the range of block price
	int investmentUL = 4;		// set the upper limit of investment
	std::string owner="NA";		//inicialize the owner, this used to save the owner name
};

//#endif