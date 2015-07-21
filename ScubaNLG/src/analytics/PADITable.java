package analytics;

/**
 *This class implements the PADI recreational dive planner.
 * @author  ssripada
 */
public class PADITable {
  static double feetToMeters = 0.3048;
  static double metersToFeet = 3.2808;
  static double safeDepthLimitInMeters = 42;
  static double safeDepthMinimumInMeters = 12;//approximately corresponds to 35 feet
  static double PADITableStartingDepthInMeters = 10;
  static double safeTimeBetweenDivesInMinutes = 720;
  static double avgAscentLimit = 6;
  static double excessBottomTimeLimit = 5;//min, obtained from PADI dive planner
  static double depthEquivalence = 0.5;//two depth values are considered same if they differ by 0.5 meter
  static double depthEquivalence2 = 1.0;//two depth values are considered same if they differ by 1.0 meter
  static double depthEquivalence3 = 2.0;//two depth values are considered same if they differ by 2.0 meter

  public PADITable() {
    //null constructor
  }

  public static long getNDL(double depth){
    depth = depth*metersToFeet;
    if(depth<=35){
      return 205;
    }
    else if((depth>35)&&(depth<=40)){
      return 140;
    }
    else if((depth>40)&&(depth<=50)){
      return 80;
    }
    else if((depth>50)&&(depth<=60)){
      return 55;
    }
    else if((depth>60)&&(depth<=70)){
      return 40;
    }
    else if((depth>70)&&(depth<=80)){
      return 30;
    }
    else if((depth>80)&&(depth<=90)){
      return 25;
    }
    else if((depth>90)&&(depth<=100)){
      return 20;
    }
    else if((depth>100)&&(depth<=110)){
      return 15;
    }
    else if((depth>110)&&(depth<=120)){
      return 13;
    }
    else if((depth>120)&&(depth<=130)){
      return 10;
    }
    else if((depth>130)&&(depth<=140)){
      return 8;
    }
    else return 0;
  }


  public static boolean needSafetyStop(double depth,long time){
    depth = depth*metersToFeet;
    if(depth<=35){
      if(time>=152)
        return true;
      else return false;
    }
    else if((depth>35)&&(depth<=40)){
      if(time>=111)
        return true;
      else return false;
    }
    else if((depth>40)&&(depth<=50)){
      if(time>=67)
        return true;
      else return false;
    }
    else if((depth>50)&&(depth<=60)){
     if(time>=49)
        return true;
      else return false;
    }
    else if((depth>60)&&(depth<=70)){
      if(time>=35)
        return true;
      else return false;
    }
    else if((depth>70)&&(depth<=80)){
      if(time>=26)
        return true;
      else return false;
    }
    else if((depth>80)&&(depth<=90)){
      if(time>=22)
        return true;
      else return false;
    }
    else return true;
  }

  public static char getStartPressureGroup(double depth,long time){
    depth = depth*metersToFeet;
    if(depth<=35){
      if(time<=10)
        return 'A';
      else if((time>10)&&(time<=19))
        return 'B';
      else if((time>19)&&(time<=25))
        return 'C';
      else if((time>25)&&(time<=29))
        return 'D';
      else if((time>29)&&(time<=32))
        return 'E';
      else if((time>32)&&(time<=36))
        return 'F';
      else if((time>36)&&(time<=40))
        return 'G';
      else if((time>40)&&(time<=44))
        return 'H';
      else if((time>44)&&(time<=48))
        return 'I';
      else if((time>48)&&(time<=52))
        return 'J';
      else if((time>52)&&(time<=57))
        return 'K';
      else if((time>57)&&(time<=62))
        return 'L';
      else if((time>62)&&(time<=67))
        return 'M';
      else if((time>67)&&(time<=73))
        return 'N';
      else if((time>73)&&(time<=79))
        return 'O';
      else if((time>79)&&(time<=85))
        return 'P';
      else if((time>85)&&(time<=92))
        return 'Q';
      else if((time>92)&&(time<=100))
        return 'R';
      else if((time>100)&&(time<=108))
        return 'S';
      else if((time>108)&&(time<=117))
        return 'T';
      else if((time>117)&&(time<=127))
        return 'U';
      else if((time>127)&&(time<=139))
        return 'V';
      else if((time>139)&&(time<=152))
        return 'W';
      else if((time>152)&&(time<=168))
        return 'X';
      else if((time>168)&&(time<=188))
        return 'Y';
      else if((time>188)&&(time<=205))
        return 'Z';
      else return '$';
    }
    else if((depth>35)&&(depth<=40)){
      if(time<=9)
        return 'A';
      else if((time>9)&&(time<=16))
        return 'B';
      else if((time>16)&&(time<=22))
        return 'C';
      else if((time>22)&&(time<=25))
        return 'D';
      else if((time>25)&&(time<=27))
        return 'E';
      else if((time>27)&&(time<=31))
        return 'F';
      else if((time>31)&&(time<=34))
        return 'G';
      else if((time>34)&&(time<=37))
        return 'H';
      else if((time>37)&&(time<=40))
        return 'I';
      else if((time>40)&&(time<=44))
        return 'J';
      else if((time>44)&&(time<=48))
        return 'K';
      else if((time>48)&&(time<=51))
        return 'L';
      else if((time>51)&&(time<=55))
        return 'M';
      else if((time>55)&&(time<=60))
        return 'N';
      else if((time>60)&&(time<=64))
        return 'O';
      else if((time>64)&&(time<=69))
        return 'P';
      else if((time>69)&&(time<=74))
        return 'Q';
      else if((time>74)&&(time<=79))
        return 'R';
      else if((time>79)&&(time<=85))
        return 'S';
      else if((time>85)&&(time<=91))
        return 'T';
      else if((time>91)&&(time<=97))
        return 'U';
      else if((time>97)&&(time<=104))
        return 'V';
      else if((time>104)&&(time<=111))
        return 'W';
      else if((time>111)&&(time<=120))
        return 'X';
      else if((time>120)&&(time<=129))
        return 'Y';
      else if((time>129)&&(time<=140))
        return 'Z';
      else return '$';
    }
    else if((depth>40)&&(depth<=50)){
      if(time<=7)
        return 'A';
      else if((time>7)&&(time<=13))
        return 'B';
      else if((time>13)&&(time<=17))
        return 'C';
      else if((time>17)&&(time<=19))
        return 'D';
      else if((time>19)&&(time<=21))
        return 'E';
      else if((time>21)&&(time<=24))
        return 'F';
      else if((time>24)&&(time<=26))
        return 'G';
      else if((time>26)&&(time<=28))
        return 'H';
      else if((time>28)&&(time<=31))
        return 'I';
      else if((time>31)&&(time<=33))
        return 'J';
      else if((time>33)&&(time<=36))
        return 'K';
      else if((time>36)&&(time<=39))
        return 'L';
      else if((time>39)&&(time<=41))
        return 'M';
      else if((time>41)&&(time<=44))
        return 'N';
      else if((time>44)&&(time<=47))
        return 'O';
      else if((time>47)&&(time<=50))
        return 'P';
      else if((time>50)&&(time<=53))
        return 'Q';
      else if((time>53)&&(time<=57))
        return 'R';
      else if((time>57)&&(time<=60))
        return 'S';
      else if((time>60)&&(time<=63))
        return 'T';
      else if((time>63)&&(time<=67))
        return 'U';
      else if((time>67)&&(time<=71))
        return 'V';
      else if((time>71)&&(time<=75))
        return 'W';
      else if((time>75)&&(time<=80))
        return 'X';
      else return '$';
    }
    else if((depth>50)&&(depth<=60)){
      if(time<=6)
        return 'A';
      else if((time>6)&&(time<=11))
        return 'B';
      else if((time>11)&&(time<=14))
        return 'C';
      else if((time>14)&&(time<=16))
        return 'D';
      else if((time>16)&&(time<=17))
        return 'E';
      else if((time>17)&&(time<=19))
        return 'F';
      else if((time>19)&&(time<=21))
        return 'G';
      else if((time>21)&&(time<=23))
        return 'H';
      else if((time>23)&&(time<=25))
        return 'I';
      else if((time>25)&&(time<=27))
        return 'J';
      else if((time>27)&&(time<=29))
        return 'K';
      else if((time>29)&&(time<=31))
        return 'L';
      else if((time>31)&&(time<=33))
        return 'M';
      else if((time>33)&&(time<=35))
        return 'N';
      else if((time>35)&&(time<=37))
        return 'O';
      else if((time>37)&&(time<=39))
        return 'P';
      else if((time>39)&&(time<=42))
        return 'Q';
      else if((time>42)&&(time<=44))
        return 'R';
      else if((time>44)&&(time<=47))
        return 'S';
      else if((time>47)&&(time<=49))
        return 'T';
      else if((time>49)&&(time<=52))
        return 'U';
      else if((time>52)&&(time<=54))
        return 'V';
      else if((time>54)&&(time<=55))
        return 'W';
      else return '$';
    }
    else if((depth>60)&&(depth<=70)){
      if(time<=5)
        return 'A';
      else if((time>5)&&(time<=9))
        return 'B';
      else if((time>9)&&(time<=12))
        return 'C';
      else if((time>12)&&(time<=13))
        return 'D';
      else if((time>13)&&(time<=15))
        return 'E';
      else if((time>15)&&(time<=16))
        return 'F';
      else if((time>16)&&(time<=18))
        return 'G';
      else if((time>18)&&(time<=19))
        return 'H';
      else if((time>19)&&(time<=21))
        return 'I';
      else if((time>21)&&(time<=22))
        return 'J';
      else if((time>22)&&(time<=24))
        return 'K';
      else if((time>24)&&(time<=26))
        return 'L';
      else if((time>26)&&(time<=27))
        return 'M';
      else if((time>27)&&(time<=29))
        return 'N';
      else if((time>29)&&(time<=31))
        return 'O';
      else if((time>31)&&(time<=33))
        return 'P';
      else if((time>33)&&(time<=35))
        return 'Q';
      else if((time>35)&&(time<=36))
        return 'R';
      else if((time>36)&&(time<=38))
        return 'S';
      else if((time>38)&&(time<=40))
        return 'T';
      else return '$';
    }
    else if((depth>70)&&(depth<=80)){
      if(time<=4)
        return 'A';
      else if((time>4)&&(time<=8))
        return 'B';
      else if((time>8)&&(time<=10))
        return 'C';
      else if((time>10)&&(time<=11))
        return 'D';
      else if((time>11)&&(time<=13))
        return 'E';
      else if((time>13)&&(time<=14))
        return 'F';
      else if((time>14)&&(time<=15))
        return 'G';
      else if((time>15)&&(time<=17))
        return 'H';
      else if((time>17)&&(time<=18))
        return 'I';
      else if((time>18)&&(time<=19))
        return 'J';
      else if((time>19)&&(time<=21))
        return 'K';
      else if((time>21)&&(time<=22))
        return 'L';
      else if((time>22)&&(time<=23))
        return 'M';
      else if((time>23)&&(time<=25))
        return 'N';
      else if((time>25)&&(time<=26))
        return 'O';
      else if((time>26)&&(time<=28))
        return 'P';
      else if((time>28)&&(time<=29))
        return 'Q';
      else if((time>29)&&(time<=30))
        return 'R';
      else return '$';
    }
    else if((depth>80)&&(depth<=90)){
      if(time<=4)
        return 'A';
      else if((time>4)&&(time<=7))
        return 'B';
      else if((time>7)&&(time<=9))
        return 'C';
      else if((time>9)&&(time<=10))
        return 'D';
      else if((time>10)&&(time<=11))
        return 'E';
      else if((time>11)&&(time<=12))
        return 'F';
      else if((time>12)&&(time<=13))
        return 'G';
      else if((time>13)&&(time<=15))
        return 'H';
      else if((time>15)&&(time<=16))
        return 'I';
      else if((time>16)&&(time<=17))
        return 'J';
      else if((time>17)&&(time<=18))
        return 'K';
      else if((time>18)&&(time<=19))
        return 'L';
      else if((time>19)&&(time<=21))
        return 'M';
      else if((time>21)&&(time<=22))
        return 'N';
      else if((time>22)&&(time<=23))
        return 'O';
      else if((time>23)&&(time<=24))
        return 'P';
      else if((time>24)&&(time<=25))
        return 'Q';
      else return '$';
    }
    else if((depth>90)&&(depth<=100)){
      if(time<=3)
        return 'A';
      else if((time>3)&&(time<=6))
        return 'B';
      else if((time>6)&&(time<=8))
        return 'C';
      else if((time>8)&&(time<=9))
        return 'D';
      else if((time>9)&&(time<=10))
        return 'E';
      else if((time>10)&&(time<=11))
        return 'F';
      else if((time>11)&&(time<=12))
        return 'G';
      else if((time>12)&&(time<=13))
        return 'H';
      else if((time>13)&&(time<=14))
        return 'I';
      else if((time>14)&&(time<=15))
        return 'J';
      else if((time>15)&&(time<=16))
        return 'K';
      else if((time>16)&&(time<=17))
        return 'L';
      else if((time>17)&&(time<=18))
        return 'M';
      else if((time>18)&&(time<=19))
        return 'N';
      else if((time>19)&&(time<=20))
        return 'O';
      else return '$';
    }
    else if((depth>100)&&(depth<=110)){
      if(time<=3)
        return 'A';
      else if((time>3)&&(time<=6))
        return 'B';
      else if((time>6)&&(time<=7))
        return 'C';
      else if((time>7)&&(time<=8))
        return 'D';
      else if((time>8)&&(time<=9))
        return 'E';
      else if((time>9)&&(time<=10))
        return 'F';
      else if((time>10)&&(time<=11))
        return 'G';
      else if((time>11)&&(time<=12))
        return 'H';
      else if((time>12)&&(time<=13))
        return 'I';
      else if((time>13)&&(time<=14))
        return 'K';
      else if((time>14)&&(time<=15))
        return 'L';
      else if((time>15)&&(time<=16))
        return 'M';
      else return '$';
    }
    else if((depth>110)&&(depth<=120)){
      if(time<=3)
         return 'A';
       else if((time>3)&&(time<=5))
         return 'B';
       else if((time>5)&&(time<=6))
         return 'C';
       else if((time>6)&&(time<=7))
         return 'D';
       else if((time>7)&&(time<=8))
         return 'E';
       else if((time>8)&&(time<=9))
         return 'F';
       else if((time>9)&&(time<=10))
         return 'G';
       else if((time>10)&&(time<=11))
         return 'H';
       else if((time>11)&&(time<=12))
         return 'J';
       else if((time>12)&&(time<=13))
         return 'K';
       else return '$';
    }
    else if((depth>120)&&(depth<=130)){
      if(time<=3)
         return 'A';
       else if((time>3)&&(time<=5))
         return 'B';
       else if((time>5)&&(time<=6))
         return 'C';
       else if((time>6)&&(time<=7))
         return 'D';
       else if((time>7)&&(time<=8))
         return 'F';
       else if((time>8)&&(time<=9))
         return 'G';
       else if((time>9)&&(time<=10))
         return 'H';
       else return '$';
    }
    else if((depth>130)&&(depth<=140)){
      if(time<=4)
         return 'B';
       else if((time>4)&&(time<=5))
         return 'C';
       else if((time>5)&&(time<=6))
         return 'D';
       else if((time>6)&&(time<=7))
         return 'E';
       else if((time>7)&&(time<=8))
         return 'F';
       else return '$';
    }
    else return '$';
  }

  public static long getRNT(double depth,char group){
    //here group is the End SITGroup
	  depth = depth*metersToFeet;
          System.out.println("PADI: " + depth + " " + group);
	  if(depth<=35){
		  switch(group){
		  case 'Z': return 205;
		  case 'Y': return 188;
		  case 'X': return 168;
		  case 'W': return 152;
		  case 'V': return 139;
		  case 'U': return 127;
		  case 'T': return 117;
		  case 'S': return 106;
		  case 'R': return 100;
		  case 'Q': return 92;
		  case 'P': return 85;
		  case 'O': return 79;
		  case 'N': return 73;
		  case 'M': return 67;
		  case 'L': return 62;
		  case 'K': return 57;
		  case 'J': return 52;
		  case 'I': return 48;
		  case 'H': return 44;
		  case 'G': return 40;
		  case 'F': return 36;
		  case 'E': return 32;
		  case 'D': return 29;
		  case 'C': return 25;
		  case 'B': return 19;
		  default: return 10;
		  }
	  }
	  else if((depth>35)&&(depth<=40)){
		  switch(group){
		  case 'Z': return 140;
		  case 'Y': return 129;
		  case 'X': return 120;
		  case 'W': return 111;
		  case 'V': return 104;
		  case 'U': return 97;
		  case 'T': return 91;
		  case 'S': return 85;
		  case 'R': return 79;
		  case 'Q': return 74;
		  case 'P': return 69;
		  case 'O': return 64;
		  case 'N': return 60;
		  case 'M': return 55;
		  case 'L': return 51;
		  case 'K': return 48;
		  case 'J': return 44;
		  case 'I': return 40;
		  case 'H': return 37;
		  case 'G': return 34;
		  case 'F': return 31;
		  case 'E': return 27;
		  case 'D': return 25;
		  case 'C': return 22;
		  case 'B': return 16;
		  default: return 9;
		  }
	  }
	  else if((depth>40)&&(depth<=50)){
		  switch(group){
		  case 'X': return 80;
		  case 'W': return 75;
		  case 'V': return 71;
		  case 'U': return 67;
		  case 'T': return 63;
		  case 'S': return 60;
		  case 'R': return 57;
		  case 'Q': return 53;
		  case 'P': return 50;
		  case 'O': return 47;
		  case 'N': return 44;
		  case 'M': return 41;
		  case 'L': return 38;
		  case 'K': return 36;
		  case 'J': return 33;
		  case 'I': return 31;
		  case 'H': return 28;
		  case 'G': return 25;
		  case 'F': return 24;
		  case 'E': return 21;
		  case 'D': return 19;
		  case 'C': return 17;
		  case 'B': return 13;
		  default: return 7;
		  }
	  }
	  else if((depth>50)&&(depth<=60)){
		  switch(group){
		  case 'W': return 55;
		  case 'V': return 54;
		  case 'U': return 52;
		  case 'T': return 49;
		  case 'S': return 47;
		  case 'R': return 44;
		  case 'Q': return 42;
		  case 'P': return 39;
		  case 'O': return 37;
		  case 'N': return 35;
		  case 'M': return 33;
		  case 'L': return 31;
		  case 'K': return 29;
		  case 'J': return 27;
		  case 'I': return 25;
		  case 'H': return 23;
		  case 'G': return 21;
		  case 'F': return 19;
		  case 'E': return 17;
		  case 'D': return 15;
		  case 'C': return 14;
		  case 'B': return 11;
		  default: return 6;
		  }
	  }
	  else if((depth>60)&&(depth<=70)){
		  switch(group){
		  case 'T': return 40;
		  case 'S': return 38;
		  case 'R': return 36;
		  case 'Q': return 34;
		  case 'P': return 33;
		  case 'O': return 31;
		  case 'N': return 29;
		  case 'M': return 27;
		  case 'L': return 26;
		  case 'K': return 24;
		  case 'J': return 22;
		  case 'I': return 21;
		  case 'H': return 19;
		  case 'G': return 18;
		  case 'F': return 16;
		  case 'E': return 15;
		  case 'D': return 13;
		  case 'C': return 12;
		  case 'B': return 9;
		  default: return 5;
		  }
	  }
	  else if((depth>70)&&(depth<=80)){
		  switch(group){
		  case 'R': return 30;
		  case 'Q': return 29;
		  case 'P': return 28;
		  case 'O': return 26;
		  case 'N': return 25;
		  case 'M': return 23;
		  case 'L': return 22;
		  case 'K': return 21;
		  case 'J': return 19;
		  case 'I': return 18;
		  case 'H': return 17;
		  case 'G': return 15;
		  case 'F': return 14;
		  case 'E': return 13;
		  case 'D': return 11;
		  case 'C': return 10;
		  case 'B': return 8;
		  default: return 4;
		  }
	  }
	  else if((depth>80)&&(depth<=90)){
		  switch(group){
		  case 'Q': return 25;
		  case 'P': return 24;
		  case 'O': return 23;
		  case 'N': return 22;
		  case 'M': return 21;
		  case 'L': return 19;
		  case 'K': return 18;
		  case 'J': return 17;
		  case 'I': return 16;
		  case 'H': return 15;
		  case 'G': return 13;
		  case 'F': return 12;
		  case 'E': return 11;
		  case 'D': return 10;
		  case 'C': return 9;
		  case 'B': return 7;
		  default: return 4;
		  }
	  }
	  else if((depth>90)&&(depth<=100)){
		  switch(group){
		  case 'O': return 20;
		  case 'N': return 19;
		  case 'M': return 18;
		  case 'L': return 17;
		  case 'K': return 16;
		  case 'J': return 15;
		  case 'I': return 14;
		  case 'H': return 13;
		  case 'G': return 12;
		  case 'F': return 11;
		  case 'E': return 10;
		  case 'D': return 9;
		  case 'C': return 8;
		  case 'B': return 6;
		  default: return 3;
		  }
	  }
	  else if((depth>100)&&(depth<=110)){
		  switch(group){
		  case 'M': return 16;
		  case 'L': return 15;
		  case 'K': return 14;
		  case 'J': return 14;
		  case 'I': return 13;
		  case 'H': return 12;
		  case 'G': return 11;
		  case 'F': return 10;
		  case 'E': return 9;
		  case 'D': return 8;
		  case 'C': return 7;
		  case 'B': return 6;
		  default: return 3;
		  }
	  }
	  else if((depth>110)&&(depth<=120)){
		  switch(group){
		  case 'K': return 13;
		  case 'J': return 12;
		  case 'I': return 12;
		  case 'H': return 11;
		  case 'G': return 10;
		  case 'F': return 9;
		  case 'E': return 8;
		  case 'D': return 7;
		  case 'C': return 6;
		  case 'B': return 5;
                  case 'A': return 3;
		  default: return 3;
		  }
	  }
	  else if((depth>120)&&(depth<=130)){
		  switch(group){
		  case 'H': return 10;
		  case 'G': return 9;
		  case 'F': return 8;
		  case 'E': return 8;
		  case 'D': return 7;
		  case 'C': return 6;
		  case 'B': return 5;
		  default: return 3;
		  }
	  }
	  else if((depth>130)&&(depth<=140)){//arbitrary
            switch(group){
                  case 'G': return 9;
                  case 'F': return 8;
                  case 'E': return 8;
                  case 'D': return 7;
                  case 'C': return 6;
                  case 'B': return 5;
                  default: return 3;
                  }
	  }
          else return 7;//approximate figure
  }


  public static long getANDL(double depth,char group){
    //here group is the End SITGroup and depth is the depth of the repeat dive
	  depth = depth*metersToFeet;
	  if(depth<=35){
		  switch(group){
		  case 'Y': return 17;
		  case 'X': return 37;
		  case 'W': return 53;
		  case 'V': return 68;
		  case 'U': return 78;
		  case 'T': return 88;
		  case 'S': return 97;
		  case 'R': return 105;
		  case 'Q': return 112;
		  case 'P': return 120;
		  case 'O': return 125;
		  case 'N': return 132;
		  case 'M': return 138;
		  case 'L': return 143;
		  case 'K': return 148;
		  case 'J': return 153;
		  case 'I': return 157;
		  case 'H': return 161;
		  case 'G': return 165;
		  case 'F': return 169;
		  case 'E': return 173;
		  case 'D': return 176;
		  case 'C': return 180;
		  case 'B': return 186;
		  default: return 195;
		  }
	  }
	  else if((depth>35)&&(depth<=40)){
		  switch(group){
		  case 'Y': return 11;
		  case 'X': return 20;
		  case 'W': return 29;
		  case 'V': return 38;
		  case 'U': return 43;
		  case 'T': return 49;
		  case 'S': return 55;
		  case 'R': return 61;
		  case 'Q': return 66;
		  case 'P': return 71;
		  case 'O': return 76;
		  case 'N': return 80;
		  case 'M': return 85;
		  case 'L': return 89;
		  case 'K': return 92;
		  case 'J': return 95;
		  case 'I': return 100;
		  case 'H': return 103;
		  case 'G': return 106;
		  case 'F': return 109;
		  case 'E': return 112;
		  case 'D': return 115;
		  case 'C': return 118;
		  case 'B': return 124;
		  default: return 131;
		  }
	  }
	  else if((depth>40)&&(depth<=50)){
		  switch(group){
		  case 'W': return 5;
		  case 'V': return 9;
		  case 'U': return 13;
		  case 'T': return 17;
		  case 'S': return 20;
		  case 'R': return 23;
		  case 'Q': return 27;
		  case 'P': return 30;
		  case 'O': return 33;
		  case 'N': return 36;
		  case 'M': return 39;
		  case 'L': return 42;
		  case 'K': return 44;
		  case 'J': return 47;
		  case 'I': return 49;
		  case 'H': return 52;
		  case 'G': return 54;
		  case 'F': return 56;
		  case 'E': return 59;
		  case 'D': return 61;
		  case 'C': return 63;
		  case 'B': return 67;
		  default: return 73;
		  }
	  }
	  else if((depth>50)&&(depth<=60)){
		  switch(group){
		  case 'V': return 1;
		  case 'U': return 3;
		  case 'T': return 6;
		  case 'S': return 9;
		  case 'R': return 11;
		  case 'Q': return 13;
		  case 'P': return 16;
		  case 'O': return 18;
		  case 'N': return 20;
		  case 'M': return 22;
		  case 'L': return 24;
		  case 'K': return 26;
		  case 'J': return 28;
		  case 'I': return 30;
		  case 'H': return 32;
		  case 'G': return 34;
		  case 'F': return 36;
		  case 'E': return 38;
		  case 'D': return 39;
		  case 'C': return 41;
		  case 'B': return 44;
		  default: return 48;
		  }
	  }
	  else if((depth>60)&&(depth<=70)){
		  switch(group){
		  case 'S': return 2;
		  case 'R': return 4;
		  case 'Q': return 6;
		  case 'P': return 7;
		  case 'O': return 9;
		  case 'N': return 11;
		  case 'M': return 13;
		  case 'L': return 14;
		  case 'K': return 16;
		  case 'J': return 18;
		  case 'I': return 19;
		  case 'H': return 21;
		  case 'G': return 22;
		  case 'F': return 24;
		  case 'E': return 25;
		  case 'D': return 27;
		  case 'C': return 29;
		  case 'B': return 31;
		  default: return 35;
		  }
	  }
	  else if((depth>70)&&(depth<=80)){
		  switch(group){
		  case 'P': return 2;
		  case 'O': return 4;
		  case 'N': return 5;
		  case 'M': return 7;
		  case 'L': return 8;
		  case 'K': return 9;
		  case 'J': return 11;
		  case 'I': return 12;
		  case 'H': return 13;
		  case 'G': return 15;
		  case 'F': return 16;
		  case 'E': return 17;
		  case 'D': return 19;
		  case 'C': return 20;
		  case 'B': return 22;
		  default: return 24;
		  }
	  }
	  else if((depth>80)&&(depth<=90)){
		  switch(group){
		  case 'O': return 2;
		  case 'N': return 3;
		  case 'M': return 4;
		  case 'L': return 6;
		  case 'K': return 7;
		  case 'J': return 8;
		  case 'I': return 9;
		  case 'H': return 10;
		  case 'G': return 12;
		  case 'F': return 13;
		  case 'E': return 14;
		  case 'D': return 15;
		  case 'C': return 16;
		  case 'B': return 19;
		  default: return 21;
		  }
	  }
	  else if((depth>90)&&(depth<=100)){
		  switch(group){
		  case 'M': return 2;
		  case 'L': return 3;
		  case 'K': return 4;
		  case 'J': return 5;
		  case 'I': return 6;
		  case 'H': return 7;
		  case 'G': return 8;
		  case 'F': return 9;
		  case 'E': return 10;
		  case 'D': return 11;
		  case 'C': return 12;
		  case 'B': return 14;
		  default: return 17;
		  }
	  }
	  else if((depth>100)&&(depth<=110)){
		  switch(group){
		  case 'K': return 2;
		  case 'J': return 2;
		  case 'I': return 3;
		  case 'H': return 4;
		  case 'G': return 5;
		  case 'F': return 6;
		  case 'E': return 7;
		  case 'D': return 8;
		  case 'C': return 9;
		  case 'B': return 10;
		  default: return 13;
		  }
	  }
	  else if((depth>110)&&(depth<=120)){
		  switch(group){
		  case 'H': return 2;
		  case 'G': return 3;
		  case 'F': return 4;
		  case 'E': return 5;
		  case 'D': return 6;
		  case 'C': return 7;
		  case 'B': return 8;
		  default: return 10;
		  }
	  }
	  else if((depth>120)&&(depth<=130)){
		  switch(group){
		  case 'D': return 3;
		  case 'C': return 4;
		  case 'B': return 5;
		  default: return 7;
		  }
	  }
	  else if((depth>130)&&(depth<=140)){
            return 7;//arbitrarily set
	  }
          else
            return 1;
  }

  public static char getEndPressureGroup(char group,long sit){
	  int hour = (int)sit/60;
	  long tmp = sit%60;
	  int min = (int)tmp;
	  Integer Hour = new Integer(hour);
	  Integer Min = new Integer(min);
	  String minString = "";
	  if(Min<10)
		  minString = "0"+Min.toString();
	  String sits = Hour.toString()+":"+minString;//Min.toString();
	  //System.out.println("SITS: "+sits);
	  switch(group){
	  case 'Z': {
		  if((sits.compareTo("3:00")>=0)&&(sits.compareTo("6:00")<=0))
				  return 'A';
		  else return 'B';
	  }
	  case 'Y': {
		  if((sits.compareTo("2:57")>=0)&&(sits.compareTo("5:57")<=0))
			  return 'A';
		  else return 'B';
	  }
	  case 'X': {
		  if((sits.compareTo("3:00")>=0)&&(sits.compareTo("6:00")<=0))
			  return 'A';
		  else return 'B';
	  }
	  case 'W': {
		  if((sits.compareTo("3:00")>=0)&&(sits.compareTo("6:00")<=0))
			  return 'A';
		  else return 'B';
	  }
	  case 'V': {
		  if((sits.compareTo("3:00")>=0)&&(sits.compareTo("6:00")<=0))
			  return 'A';
		  else return 'B';
	  }
	  case 'U': {
		  if((sits.compareTo("3:00")>=0)&&(sits.compareTo("6:00")<=0))
			  return 'A';
		  else return 'B';
	  }
	  case 'T': {
		  if((sits.compareTo("3:00")>=0)&&(sits.compareTo("6:00")<=0))
			  return 'A';
		  else return 'B';
	  }
	  case 'S': {
		  if((sits.compareTo("3:00")>=0)&&(sits.compareTo("6:00")<=0))
			  return 'A';
		  else return 'B';
	  }
	  case 'R': {
		  if((sits.compareTo("3:00")>=0)&&(sits.compareTo("6:00")<=0))
			  return 'A';
		  else return 'B';
	  }
	  case 'Q': {
		  if((sits.compareTo("3:00")>=0)&&(sits.compareTo("6:00")<=0))
			  return 'A';
		  else return 'B';
	  }
	  case 'P': {
		  if((sits.compareTo("3:00")>=0)&&(sits.compareTo("6:00")<=0))
			  return 'A';
		  else return 'B';
	  }
	  case 'O': {
		  if((sits.compareTo("3:00")>=0)&&(sits.compareTo("6:00")<=0))
			  return 'A';
		  else return 'B';
	  }
	  case 'N': {
		  if((sits.compareTo("3:00")>=0)&&(sits.compareTo("6:00")<=0))
			  return 'A';
		  else return 'B';
	  }
	  case 'M': {
		  if((sits.compareTo("3:00")>=0)&&(sits.compareTo("6:00")<=0))
			  return 'A';
		  else return 'B';
	  }
	  case 'L': {
		  if((sits.compareTo("3:00")>=0)&&(sits.compareTo("6:00")<=0))
			  return 'A';
		  else return 'B';
	  }
	  case 'K': {
		  if((sits.compareTo("3:00")>=0)&&(sits.compareTo("6:00")<=0))
			  return 'A';
		  else return 'B';
	  }
	  case 'J': {
		  if((sits.compareTo("3:00")>=0)&&(sits.compareTo("6:00")<=0))
			  return 'A';
		  else return 'B';
	  }
	  case 'I': {
		  if((sits.compareTo("3:00")>=0)&&(sits.compareTo("6:00")<=0))
			  return 'A';
		  else return 'B';
	  }
	  case 'H': {
		  if((sits.compareTo("3:00")>=0)&&(sits.compareTo("6:00")<=0))
			  return 'A';
		  else return 'B';
	  }
	  case 'G': {
		  if((sits.compareTo("3:00")>=0)&&(sits.compareTo("6:00")<=0))
			  return 'A';
		  else return 'B';
	  }
	  case 'F': {
		  if((sits.compareTo("3:00")>=0)&&(sits.compareTo("6:00")<=0))
			  return 'A';
		  else return 'B';
	  }
	  case 'E': {
		  if((sits.compareTo("3:00")>=0)&&(sits.compareTo("6:00")<=0))
			  return 'A';
		  else return 'B';
	  }
	  case 'D': {
		  if((sits.compareTo("3:00")>=0)&&(sits.compareTo("6:00")<=0))
			  return 'A';
		  else return 'B';
	  }
	  case 'C': {
		  if((sits.compareTo("1:10")>=0)&&(sits.compareTo("4:10")<=0))
			  return 'A';
		  else if((sits.compareTo("0:22")>=0)&&(sits.compareTo("1:10")<=0))
			  return 'B';
		  else if((sits.compareTo("0:00")>=0)&&(sits.compareTo("0:21")<=0))
			  return 'C';
		  else return '$';
	  }
	  case 'B': {
		  if((sits.compareTo("0:48")>=0)&&(sits.compareTo("3:48")<=0))
			  return 'A';
		  else if((sits.compareTo("0:00")>=0)&&(sits.compareTo("0:48")<=0))
			  return 'B';
		  else return '$';
	  }
	  default: {
		  if((sits.compareTo("0:00")>=0)&&(sits.compareTo("3:00")<=0))
			  return 'A';
		  else return '$';
	  }
	  }
  }
}
