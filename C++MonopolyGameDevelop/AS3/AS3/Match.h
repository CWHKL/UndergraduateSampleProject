#pragma once					// Conditional compilation
#include "Player.h"
#include <iostream>
#include "Block.h"
//#include < vector> 
#include "Match.h"
#include <string>				//Support the output of string
#include <fstream>

class Match
{
public:
	//========================================================================
	//Basic function
	Match();//The cunstructor
	~Match();//The distructor
	//========================================================================
	//Menu function
	void startMenu();           //start menu
	void round();               //Round of the player and the AI which will loop during the game
	void saveOrnot();           //ask the user to save the game
	void gameLoad();            //Load game from file
	void gameSave();            //Save game to file
	//========================================================================
	//Dis play function used in menu
	void display();				//display the situation of player
	void mapdisplay();          //display the game map
	void mapdisplay2();
	std::string standerString(std::string a);// used to change the string into needed length
	void displayBlock(int a);        //Display the situation of the block
	//========================================================================
	//Calculate function used in menu
	int checkneighbor(int n);	//check the neighbor number
	int calculateCost(int n);	//calculate the cost in this block
	//========================================================================
	//Varibles
	int dice = 6;
	int blocknumm=20;			//set the block number of the game
	int bouns = 200;			//set the bonus get at the start block
private:
	//========================================================================
	//Varibles
	Player player;              //The player class to save player
	Player AI;                  //The player class to save AI
	Block map[20];              //Use array to save the blocks in the game
	//========================================================================
	//learning note
	//std::vector <Block> map;
	//Match &match;               //Why this can not use->(Can not inincialize it self)
};

