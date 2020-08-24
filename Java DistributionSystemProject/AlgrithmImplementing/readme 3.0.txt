Qiuck start
	Check leader elect methods run situation:
		use COMP212AS1 Project floder
			step1: run COMP212AS1.java
			step2: input the node number you want in the network
			step3: in put the alpha you want to the ID genaration
			step4: input how would you like to genarate ID (1:random, 2:clockwise, 3:counterclockwise)
			The system will run RCL and HS to the network and show all the process and results.
	
	Check analyse results of LDR and HS
		use Folder COMP212AS1_Analyse as project floder
			step1: import jfreechart-1.0.19.jar and jcommon-1.0.23.jar from \jfreechart-1.0.19\lib
			step2: run LineChartEx.java which include main
			step3: input node number and alpha of id generation to see all value of once simulation 
			step4: input node number, ID genaration function, graph type to see the analyse result graph
		For further test:
			getResultOfsingleSimulate();  in main support step3 you could change value to try it as you wish
				int n = 1000;//node number
        				int n2 = 3;//alpha
        			showGraph(); support step4, you could edit variable in createDataset() to test it as you wish
				int s=0;//ID generate direction 0 for random, 1 clockwise, 2 counter clockwise
        				int cut = 20;// the segment in the graph
        				int nodeLimit = 100;// node number
        				int a = 3;// alpha in ID generation
        				int repeatExp = 3;// experement repeat number
        				int p=0; //graph catogory

Other basic information of package:
Packages
	Main
		COMP212AS1.java
	Inner function 
		Nodes.java (network and nodes)
		NodePackage.java (message package used in HS)
	Old version function backups (no use)
		Savetmp.java

important functions (These function is the components of simulate and analyse)
	genarate network with n nodes (a=alpha):
		GrnerateNodesRandomID(int n, int a)
		GrnerateNodesClockwiseID(int n, int a)
		GrnerateNodesCounterclockwiseID(int n, int a)
	run LCR or HR in such network
		Task1LCR(Nodes network1)
		Task2HS(Nodes network1)