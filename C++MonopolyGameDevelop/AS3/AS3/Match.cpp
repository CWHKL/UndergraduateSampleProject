#include "stdafx.h"
#include "Match.h"
#include <iostream>
#include "Block.h"
#include <string>

Match::Match()
{
	//using namespace std;
	for (int i = 0; i<blocknumm; i++) // genarate map
	{
		map[i]=Block(i);
		//map.push_back[i](a);
		//cout << obj[i] << ",";
	}
	map[0].setStartPoint();
}


Match::~Match()
{
}

void Match::startMenu(){
	system("cls");//clear the screen 
	//std::cout << "This is a Monopoly Game" << std::endl;
	std::cout << "Please Input your name "<< std::endl;
	std::string a;
	std::cin >>  a;  //(debug record)#include <string> could solve the bug
	player=Player(a);
	std::string b="AI";
	AI = Player(b);

	round();
}

int Match::checkneighbor(int n) {
	int ow = 0;                       //calculator
	std::string a=map[n].getowner();
	std::string b=map[n+1].getowner();
	std::string c= map[n-1].getowner();
	if (a==b){
		ow++;
	}
	if (a == c) {
		ow++;
	}
	return ow;
}

int Match::calculateCost(int n) {
	int cost = 0;
	cost= map[n].getPrice() * (0.1+0.05 * checkneighbor(n)+0.05*map[n].getInvest());
	return cost;
}

void Match::displayBlock(int a) {
	int ss1=map[a].getblocknumber();
	std::string ss2=map[a].getowner();
	int ss3=map[a].getPrice();
	int ss4=map[a].getInvest();
	std::cout << "|-----------------------|"<< std::endl;
	std::cout << "|Block Information:     |" << std::endl;
	std::cout << "|-----------------------|" << std::endl;
	std::cout << "|Number: " <<ss1<< std::endl;
	std::cout << "|Owner: " <<ss2<< std::endl;
	std::cout << "|Price: " <<ss3<< std::endl;
	std::cout << "|Invest Level: " <<ss4<< std::endl;
	std::cout << "|-----------------------|" << std::endl;
}

void Match::mapdisplay() {
	using namespace std;
	cout << "|-----------------------|" << "   "<<player.getname()<<endl;
	for (int i = 0; i < 8; i++) {
		cout << "|" << (map[i].getowner()).substr(0, 2);
	}
	cout<<"|"<<endl;
	cout << "|00|01|02|03|04|05|06|07|" << "   Money:" << player.getmoney()<<endl;
	

	cout << "|-----------------------|" <<  endl;

	cout << "|" << map[19].getowner().substr(0, 2) << "|                 |" << (map[8].getowner()).substr(0, 2) << "|" <<"   Location:" << player.getlocation() << endl;
	cout << "|19|    Monopoly     |08|" << endl;
	cout << "|---                 ---|" << endl;
	cout << "|" << map[18].getowner().substr(0, 2) << "|                 |" << (map[9].getowner()).substr(0, 2) << "|" << endl;
	cout << "|18|                 |09|" << "   " << AI.getname()<< endl;

	cout << "|---                 ---|" <<  endl;

	for (int j = 17; j >= 10; j--) {
		string mm1=map[j].getowner();
		cout << "|" << mm1.substr(0, 2);
	}
	cout << "|" <<"   Money:" << AI.getmoney() << endl;
	cout << "|017|016|015|014|013|012|011|010|" <<  endl;

	cout << "|-----------------------|" << "   Location:" << AI.getlocation()<<endl;

}
//======================================================================================================================================
std::string  Match::standerString(std::string a) {
	if (a.size() >= 3) {
		a = a.substr(0, 3);
	}
	else if (a.size() == 2) {
		a = a + " ";
	}
	else {
		a = a + "  ";
	}
	return a;
}
void Match::mapdisplay2(){
	using namespace std;
	cout << "|-------------------------------|" << "   " << player.getname() << endl;
	for (int i = 0; i < 8; i++) {
		cout << "|" << standerString(map[i].getowner());
	}
	cout << "|" << endl;

	for (int i = 0; i < 8; i++) {
		cout << "|" << (map[i].getPrice());
	}
	cout << "|" << endl;

	cout << "|000|001|002|003|004|005|006|007|" << "   Money:" << player.getmoney() << endl;


	cout << "|-------------------------------|" << endl;

	cout << "|" << standerString(map[19].getowner()) << "|                       |" << standerString(map[8].getowner()) << "|" << "   Location:" << player.getlocation() << endl;
	cout << "|" << map[19].getPrice() << "|                       |" << map[8].getPrice() << "|"  << endl;
	cout << "|019|       Monopoly        |008|" << endl;
	cout << "|---                         ---|" << endl;
	cout << "|" << standerString(map[18].getowner()) << "|                       |" << standerString(map[9].getowner()) << "|" << endl;
	cout << "|" << map[18].getPrice() << "|                       |" << map[9].getPrice() << "|" << endl;
	cout << "|018|                       |009|" << "   " << AI.getname() << endl;

	cout << "|---                         ---|" << endl;

	for (int j = 17; j >= 10; j--) {
		string mm1 = standerString(map[j].getowner());
		cout << "|" << mm1.substr(0, 3);
	}
	cout << "|" << "   Money:" << AI.getmoney() << endl;
	for (int j = 17; j >= 10; j--) {
		cout << "|" << (map[j].getPrice());
	}
	cout << "|" << endl;
	cout << "|017|016|015|014|013|012|011|010|" << endl;

	cout << "|-------------------------------|" << "   Location:" << AI.getlocation() << endl;
}
//======================================================================================================================================
void Match::round(){
	using namespace std;

	for(;;){
		system("cls");//clear the screen and display the map
		mapdisplay2();
	//=====================================================================================================================
	//the round of player
	//check location to prevent location overflow
	//player.getlocation;
	int p1 = player.getlocation();
	if ( p1>= blocknumm)//check the location of player
	{
		player.location -= blocknumm;  //reset the location
		player.location += 1;  //the block start from 0 so add 1
		player.getbonus();
		cout << "player " << player.getname() << " get bonus 200." << endl;
	}
	
								  
	//int a = player.getlocation();//get the location of player
	cout << "press Enter to roll the dice." << endl;
	system("pause");           //pause to let the player to roll dice when he/she liked to
	int a= (rand() % (dice - 1)) + 1;
	player.location += a;//roll dice

	int pp1 = player.getlocation();
	if (pp1 >= blocknumm)//check the location of player
	{
		player.location -= blocknumm;  //reset the location
		player.location += 1;  //the block start from 0 so add 1
		player.getbonus();
		cout << "player " << player.getname() << " get bonus 200." << endl;
		cout << "Your money remain " << player.getmoney() << endl;
	}

	int b = player.getlocation();
	cout << player.getname() << " go head " <<a<<" step "<<"and get "<< b<<" ."<< endl; //out print the result of go head
	displayBlock(b);//Display the block information
	cout << "This block belongs to " << map[b].getowner() << endl;
	cout << " Investigate: " << map[b].getInvest() << endl;

	

	if (map[b].getowner() == AI.getname())   //spend money in opponent's block
	{
		player.moneyChange(-calculateCost(b));
		cout << "Your money remain " << player.getmoney() << endl;
	}

	else if (map[b].getowner() == player.getname())   //spend money in opponent's block
	{
		if (map[b].investJudge() == 0) {
			system("pause");
			system("cls");//clear the screen and display the map
			mapdisplay2();
			cout << "Would you like to investigate (it will cost "<< map[b].getPrice() /2<<")?" << endl;//ask owner to investigate
			cout << "y or n" << endl;
			char re;
			cin >>  re;// read result
			if (re == 'y'||re=='Y') {

				map[b].investigate(player);

				int cost01 = map[b].getPrice() / 2;
				int cost11 = -cost01;
				player.moneyChange(cost11);

				cout << "You investigate " << b << " with " << map[b].getPrice()/2 <<" and it level up to "<< map[b].getInvest()<<endl;
				cout << "Your money remain " << player.getmoney() << endl;
			}
		}
	}
	else if (map[b].getPro() == 0) {
		system("pause");
		system("cls");//clear the screen and display the map
		mapdisplay2();
		cout << "Would you like to buy block ["<< b <<"] (it will cost " << map[b].getPrice() << ")?" << endl;//ask owner to investigate
		cout << "y or n" << endl;
		char re;
		cin >> re;// read result
		if (re == 'y' || re == 'Y') {
			//map[b].buy(player);  //buy the block
			map[b].changeOwner(player.getname());
			int cost02 = map[b].getPrice();
			player.moneyChange(-cost02);

			cout << "You buy " << b << " with " << map[b].getPrice() << endl;
			cout << "Your money remain " << player.getmoney() << endl;
		}
	}

	if (player.defeat() == 1)//check the defeat when defeat end the game
	{
		cout << player.getname() << " defeat." << endl;
		system("pause");
		return;
	}
	system("pause");
	//=====================================================================================================================
	//the round of AI

	system("cls");//clear the screen and display the map
	mapdisplay2();

	//int a2 = player.getlocation();//get the location of AI
	int p2 = AI.getlocation();
	if ( p2>= blocknumm)//check the location of player
	{
		AI.location -= blocknumm;  //reset the location
		AI.location += 1;  //the block start from 0 so add 1
		AI.getbonus();
		cout << "player " << AI.getname() << " get bonus 200." << endl;
		cout << "AI money remain " << AI.getmoney() << endl;
		system("pause");
	}


	int a2 = (rand() % (dice-1))+1;	//roll dice
	AI.location += a2;

	int pp2 = AI.getlocation();
	if (pp2 >= blocknumm)//check the location of player
	{
		AI.location -= blocknumm;  //reset the location
		AI.location += 1;  //the block start from 0 so add 1
		AI.getbonus();
		cout << "player " << AI.getname() << " get bonus 200." << endl;
		cout << "AI money remain " << AI.getmoney() << endl;
		system("pause");
	}

	int b2 = AI.getlocation();
	cout << AI.getname() << " go head " << a2 << " step " << "and get " << b2 << " ." << endl; //out print the result of go head
	displayBlock(b2);//Display the block information
	//system("pause");
	//system("cls");//clear the screen and display the map
	//mapdisplay();

	if (map[b2].getowner() == player.getname())   //spend money in opponent's block
	{
		AI.moneyChange(-calculateCost(b2));
		cout << "AI money remain " << AI.getmoney() << endl;
		system("pause");
	}

	else if (map[b2].getowner() == AI.getname())   //investigate 
	{
		if (map[b2].investJudge() == 0) {
			if (rand() % 100<50)//AI 50% to operate
			
			{
				map[b2].investigate(AI);
				int cost02 = map[b].getPrice() / 2;
				int cost12 = -cost02;
				AI.moneyChange(cost12);
				cout << "AI investigate " << b2 << " with " << map[b2].getPrice() / 2 <<" and it level up to " << map[b2].getInvest() << endl;
				cout << "AI money remain " << AI.getmoney() << endl;
				system("pause");
			}
		}
	}
	else if (map[b2].getPro() == 0) {
		if (rand() % 100<50) {
			//map[b2].buy(AI);  //buy the block

			map[b2].changeOwner(AI.getname());
			int cost03 = map[b2].getPrice();
			AI.moneyChange(-cost03);

			cout << "AI buy " << b2 <<" with "<< map[b2].getPrice()<< endl;
			cout << "AI money remain " << AI.getmoney() << endl;
			system("pause");
		}
	}

	if (AI.defeat() == 1)//check the defeat when defeat end the game
	{
		cout << AI.getname() << " defeat." << endl;
		system("pause");
		return;
	}
	//system("pause");
	saveOrnot();//Ask user to save the game
	}
	
}

void Match::saveOrnot() {
	std::cout << "Would you like to save the game? Input 's' to save." << std::endl;
	char a;
	std::cin >> a;
	if (a == 's'||a=='S') {
		gameSave();//save the game
		std::cout << "Game Saved" << std::endl;//remark about the save
		system("pause");
	}
}

void Match::display(){
	using namespace std;
	system("cls");
	//dis play the player situation
	cout << "##############################################################" << endl;
	cout << "|" <<player.getname() << "   $:" <<player.getmoney() << "   Location:" <<player.getlocation() <<"|" << endl;
	cout << "|" << "                                                              " << "|" << endl;
	cout << "|" << AI.getname() << "       $:" << AI.getmoney() << "   Location:" << AI.getlocation() << "|" << endl;
	cout << "##############################################################" << endl;

}

void Match::gameLoad() {
	std::ifstream fin;
	fin.open("gameData.txt");//open the file

	//Code to test read
	/*std::string a;
	std::string b;
	std::string c;
	std::string c1;
	std::string c2;
	std::string c3;
	std::string c4;*/

	if (fin.is_open()) {

		//Code to test read
		/*fin >> a;
		fin >> b;
		fin >> c;
		fin >> c1;
		fin >> c2;
		fin >> c3;
		fin >> c4;
		std::cout << a << b << c << c1 << c2 << c3 << c4 <<std::endl;*/

		int a1;
		int a2;
		std::string a3;
		int b1;
		int b2;
		std::string b3;

		fin >> a1 >> a2 >> a3;//read player information from data
		player.setLocation(a1);//set the information to the player inicialized
		player.setMoney(a2);
		player.setName(a3);

		fin >> b1 >> b2 >> b3;//read AI information from data then set them
		AI.setLocation(b1);
		AI.setMoney(b2);
		AI.setName(b3);

		//fin << AI.getlocation() << AI.getmoney() << AI.getname();

		for (int i = 0; i<blocknumm; i++) // genarate map
		{
			//inicial varibles
			int a1;
			std::string a2;
			int a3;
			int a4;
			bool a5;

			fin >>a1>>a2>>a3>>a4>>a5;  //load from file

			//set the value in the game
			/*map[i].setBlocknumber(a1);
			map[i].setOwner(a2);
			map[i].setInvestment(a3);
			map[i].setPrice(a4);
			map[i].setProperty(a5);*/
			map[i].loadBlock(a1, a4, a3, a5, a2);//Use load block to substitute above 4 lines
		}
		std::cout << "Load Successful" << std::endl;
	}
	else //no file finding
	{
		std::cout << "no such file" << std::endl;
		return;
	}
	system("pause");
	fin.close();//clost the file

	round();
}
void Match::gameSave() {
	std::ofstream fout;
	fout.open("gameData.txt");//open the file

	if (fout.is_open())
	{
		//load players
		fout << player.getlocation() << " " << player.getmoney() << " " << player.getname() << "\n";
		fout << AI.getlocation() << " " << AI.getmoney() << " " << AI.getname() << "\n";

		for (int i = 0; i<blocknumm; i++) // genarate map
		{
			int a1=map[i].getblocknumber();
			std::string a2=map[i].getowner();
			int a3 =map[i].getInvest();
			int a4=map[i].getPrice();
			bool a5=map[i].getPro();
			fout << a1 << " " << a2 << " " << a3 << " " << a4 << " " << a5 << "\n";
		
		}
	}
	//map[0].setStartPoint();

	fout.close();//close the file

	return;
}