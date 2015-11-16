package Huge_Int_M;

import java.util.Random;

public class Huge_Int_Main {
	
	//the main function
	public static void main(String[] args){
		generate_prime(40).Print_Number();
	}
	
	//generate a random integer with length in binary
	public static Huge_Number generate_random(int length){
		Huge_Number result = new Huge_Number(length);
		Random random = new Random();
		for(int i = 0; i < result.nums; i++){
			result.hexadecimal[i] = random.nextInt(16) % 16;
		}
		while(result.nums >= 1 && result.hexadecimal[result.nums - 1] == 0){
			result.nums--;
		}
		return result;
	}
	
	//judge a prime by rabin miller algorithm
	public static Boolean rabin_miller_judge_prime(Huge_Number n, int t){
		Huge_Number zero = new Huge_Number("0");
		Huge_Number one = new Huge_Number("1");
		int s = 0;
		Huge_Number r = n.minus(one);
		while(r.hexadecimal[0] % 2 == 0){
			r = r.rightMove();
			//optimize 1: s = s.plus(one);
			s++;
		}
		
		Huge_Number y = new Huge_Number();
		for(int flag = 1; flag <= t; flag++){
			Huge_Number a = new Huge_Number();
			a = generate_random(n.nums - 1);
			
			y = one;
			while(r.more_than(zero)){
				if(r.hexadecimal[0] % 2 == 1){
					y = a.multiple(y).mod(n);
				}
				r = r.rightMove();
				a = a.multiple(a).mod(n);
			}
			
			if(!y.equal(one) && !y.equal(n.minus(one))){
				//optimize 2: Huge_Number j = one;
				int j = 1;
				while(s - 1 - j >= 0 && !y.equal(n.minus(one))){
					y = y.power(2).mod(n);
					if(y.equal(one)){
						return false;
					}
					else{
						j++;
					}
				}
				if(!y.equal(n.minus(one))){
					return false;
				}
			}
			else{
				break;
			}
		}
		return true;
	}
	
	//generate a prime with length in binary
	public static Huge_Number generate_prime(int length){
		Huge_Number result = generate_random(length);
		while(!rabin_miller_judge_prime(result, 80)){
			result = generate_random(length);
		}
		return result;
	}
}
