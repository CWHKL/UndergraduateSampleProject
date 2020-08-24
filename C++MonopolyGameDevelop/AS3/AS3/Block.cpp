#include "stdafx.h"
#include "Block.h"
#include "Player.h"

Block::Block() {


}
Block::Block(int a)
{
	blocknumber = a;
	price = (rand() % (priceRrange-100))+100;// inicial 100-price range block price
	investment = 0;//inicialize the investment time
	property = 0;//inicialize the property
}
void Block::loadBlock(int bn,int pr,int in,bool pro, std::string o) {
	blocknumber=bn;  //load blocknumber from data
	price=pr;        //load price from data
	investment=in;  //load investment from data
	property=pro;   //load property from data
	owner = o;
}

void Block::setStartPoint() {
	property = 1;
	owner = "START";

}


bool Block::getPro() {
	return property;

}

void Block::changeOwner(std::string a) {
	owner = a;
}

bool Block::investJudge() {
	bool a= 0;      //a=0 indicate the block can be investigate
	if (investment >= investmentUL) {
		a = 1;      //a=0 indicate the block cannot be investigate
	}

	return a;
}

void Block::buy(Player a) {
	owner= a.getname();
	int b = -price;
	a.moneyChange(b);
	//std::cout << a.getname() << " buy " << blocknumber << std::endl;
	//std::cout << "23333333333333333333333333333333333333" << std::endl;
}

int Block::getPrice() {
	return price;
}
int Block::getInvest() {
	return investment;
}

std::string Block::getowner() {
	return owner;
}

void Block::investigate(Player a) {
	//if (a.getname() != owner) {
	//	std::cout <<  "you cannot investigate other's block "  << std::endl;  //prevent investigate mistake
	//	return;
	//}
	//int b = -(price / 2);
	//a.moneyChange(b);
	investment += 1;
}

Block::~Block()
{
}

void Block::setStartPoint(bool a) {
	property = a;
	if (a = 1) {
		owner = "ST";//set the name of start point
	}
}

int Block::getblocknumber() {
	return blocknumber;

}


void Block::bonuss(Player p) {
	if (property==1)
	{
		p.getbonus();//player get bonus when pass the starting point
	}

}

void Block::setBlocknumber(int a) {
	blocknumber = a;

}
void Block::setPrice(int a) {
	price = a;
}
void Block::setInvestment(int a) {
	investment = a;
}
void Block::setProperty(bool a) {
	property = a;
}
void Block::setOwner(std::string a) {
	owner = a;
}