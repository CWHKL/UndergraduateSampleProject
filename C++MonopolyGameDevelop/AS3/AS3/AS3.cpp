// AS3.cpp: 定义控制台应用程序的入口点。
//

#include "stdafx.h"
#include <iostream>
#include <string>
#include "Player.h" //the class player in the game
#include "Block.h"  //the class of block in the game
#include "Match.h"  //the class of game

#include <fstream>  //read and write the file
//#include <ofstream>  //Write file
//#include <ifstream>  //Read the file
//#include <fiostream.h>  


void newgame();
void test();
void menu();
void loadgame();

int main()
{
	menu();
	//newgame();
	//test;
    return 0;
}

void test() {
	using namespace std;
	int a = 12;
	cout << &a << endl;

}

void menu() {
	using namespace std;
	cout <<"This is a Monopoly Game"<< endl;
	cout << "1:New game" << endl;
	cout << "2:Load game" << endl;
	cout << "3:Quit" << endl;
	char a;
	cin >> a;
	switch (a) {
		case '1':
			newgame();//start a new game
			break; 
		case '2':
			loadgame();//load game from file then start new game
			break;
		case '3':
			return;//end the game
			break;

	}
}
void newgame() {
	Match a = Match();//Inicialize a game
	a.startMenu();// Start the game inicialize all the value
}

void loadgame() {
	
	Match a = Match();//Inicialize a game
	a.gameLoad();//Load the file then diliver them to the game
}
