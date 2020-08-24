#include "stdafx.h"
#include "Player.h"


Player::Player(std::string a)
{
	name = a; //set name
	money = 10000;
	location = 0;
	
}
void Player::moneyChange(int a) {
	money += a;
	//std::cout << name << "'s money changed to " << money<<std::endl;
	//std::cout << name << "233333333333333333333333333333" << money << std::endl;
}
int Player::getlocation() {
	return location;
}

bool Player::defeat() {
	bool a = 0;
	if (money <= 0)  // judge the money
	{
		a = 1;
	}
	return a;
}

std::string Player::getname() {
	return name;
}
int Player::getmoney() {
	return money;

}

//void Player::checkLocation(Match a) {
//	using namespace std;
//	if (location >= a.blocknumm)
//	{
//		location -= a.blocknumm;  //reset the location
//		location += 1;  //the block start from 0 so add 1
//		getbonus(a);
//		cout << "player " << name << " get bonus 1000." << endl;
//	}
//}

Player::Player() {

}

Player::~Player()
{
}

void Player::getbonus() {
	money += 200; //give player bonus 200
}

void Player::go(int a) {
	location += a;
	//std::cout << name << " go for " << a << " step "<<std::endl;
}

void Player::setLocation(int a) {
	location = a;//set location
}
void Player::setMoney(int a) {
	money = a;//set money

}
void Player::setName(std::string a) {
	name = a;//set name

}