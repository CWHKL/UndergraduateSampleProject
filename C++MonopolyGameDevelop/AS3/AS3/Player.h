#pragma once // Conditional compilation

//#ifndef _Player		// Conditional compilation
//#define _Player

#include <iomanip>		// use for setting field width
#include <time.h>		// use for generating random factor
#include <iostream>
#include <string>
class Player
{
public:
	//=====================================================================
	//Basic functions
	~Player();//distructor
	Player();//defult construct
	Player(std::string a);//generate player with input name
	//=====================================================================
	//Get value functions
	int getmoney();//get the money of the player
	int getlocation();//get the location of player
	std::string getname();//get the player name

	//=====================================================================
	//Game operation functions
	void getbonus();//get bonus when pass the starting point
	void moneyChange(int a);//change the money in the account
	void go(int a);//player walk in the map
	bool defeat();//judeg the player defeat or not return 1 for death

	//=====================================================================
	//set value functions used in loading
	void setLocation(int a);
	void setMoney(int a);
	void setName(std::string a);

	//=====================================================================
	//data
	int location;//record the location of player

private:

	//=====================================================================
	//data
	int money; // money in account
	std::string name;
};

//#endif