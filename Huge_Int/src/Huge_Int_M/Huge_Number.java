package Huge_Int_M;

public class Huge_Number {

	//the number of bits
	public int nums;
	
	//numbers on each 2-scale
	public int[] hexadecimal;
	
	/*positive or negative
	 *0:positive
	 *1:negative 
	 */
	public int flag;
	
	//constructor function
	public Huge_Number(){
		this.nums = 0;
		this.flag = 0;
		this.hexadecimal = null; 
	}
	
	//constructor function
	public Huge_Number(Huge_Number hn){
		this.nums = hn.nums;
		this.flag = hn.flag;
		this.hexadecimal = new int[this.nums];
		for(int i = 0; i < this.nums; i++){
			this.hexadecimal[i] = hn.hexadecimal[i];
		}
	}
	
	//constructor function with int parameters
	public Huge_Number(int n){
		this.nums = n;
		this.flag = 0;
		this.hexadecimal = new int[n];
		for(int i = 0; i < n; i++){
			this.hexadecimal[i] = 0;
		}
	}
	
	//constructor function with String parameters
	public Huge_Number(String n){
		this.nums = n.length();
		this.flag = 0;
		this.hexadecimal = new int[n.length()];
		for(int i = n.length() - 1; i >= 0; i--){
			if(n.charAt(this.nums - 1 - i) == '-'){
				this.flag = 1;
			}
			else if(n.charAt(this.nums - 1 - i) <= 57){
				this.hexadecimal[i] = (int)n.charAt(this.nums - 1 - i) - 48;
			}
			else{
				this.hexadecimal[i] = (int)n.charAt(this.nums - 1 - i) - 55;
			}
		}
		if(this.flag == 1){
			this.nums--;
		}
	}
	
	//print the numbers on 16-scale
	public void Print_Number(){
		if(this.nums == 0){
			System.out.print("0");
			return;
		}
		if(this.flag == 1){
			System.out.print('-');
		}
		for(int i = this.nums - 1; i >= 0; i--){
			if(this.hexadecimal[i] < 10){
				System.out.print(this.hexadecimal[i]);
			}
			else{
				System.out.print((char)(this.hexadecimal[i] + 55));
			}
		}
	}
	
	//operate +
	public Huge_Number plus(Huge_Number added){
		if((this.flag + added.flag) != 1){
			int big_nums = (this.nums > added.nums)? this.nums: added.nums;
			int small_nums = (this.nums > added.nums)? added.nums: this.nums;
			int big_flag = (this.nums > added.nums)? 0: 1;
			Huge_Number result = new Huge_Number(big_nums + 1);
			
			result.hexadecimal[0] = (this.hexadecimal[0] + added.hexadecimal[0]);
			for(int i = 1; i < small_nums; i++){
				result.hexadecimal[i] = (result.hexadecimal[i - 1] >= 16)? 1: 0;
				result.hexadecimal[i - 1] %= 16;
				result.hexadecimal[i] += (this.hexadecimal[i] + added.hexadecimal[i]);
			}
			for(int i = small_nums; i < big_nums; i++){
				result.hexadecimal[i] = (result.hexadecimal[i - 1] >= 16)? 1: 0;
				result.hexadecimal[i - 1] %= 16;
				if(big_flag == 0){
					result.hexadecimal[i] += this.hexadecimal[i];
				}
				else{
					result.hexadecimal[i] += added.hexadecimal[i];
				}
			}
			if(result.hexadecimal[big_nums - 1] >= 16){
				result.hexadecimal[big_nums - 1] -= 16;
				result.hexadecimal[big_nums] = 1;
			}
			else{
				result.nums--;
			}
			result.flag = this.flag;
			return result;
		}
		else if(this.flag == 1){
			Huge_Number hn = new Huge_Number(this);
			hn.flag = 0;
			return added.minus(hn);
		}
		else{
			Huge_Number hn = new Huge_Number(added);
			hn.flag = 0;
			return this.minus(hn);
		}
	}
	
	//operate -
	public Huge_Number minus(Huge_Number minuend){
		if((this.flag + minuend.flag) != 1){
			int big_nums = (this.nums > minuend.nums)? this.nums: minuend.nums;
			int small_nums = (this.nums > minuend.nums)? minuend.nums: this.nums;
			int big_flag = (this.nums > minuend.nums)? 0: 1;
			Huge_Number result = new Huge_Number(big_nums);
			
			result.hexadecimal[0] = (this.hexadecimal[0] - minuend.hexadecimal[0]);
			result.flag = this.flag;
			for(int i = 1; i < small_nums; i++){
				result.hexadecimal[i] = (result.hexadecimal[i - 1] < 0)? -1: 0;
				result.hexadecimal[i - 1] = (result.hexadecimal[i - 1] + 16) % 16;
				result.hexadecimal[i] += (this.hexadecimal[i] - minuend.hexadecimal[i]);
			}
			for(int i = small_nums; i < big_nums; i++){
				if(i > 0){
					result.hexadecimal[i] = (result.hexadecimal[i - 1] < 0)? -1: 0;
					result.hexadecimal[i - 1] = (result.hexadecimal[i - 1] + 16) % 16;
				}
				else{
					result.hexadecimal[i] = 0;
				}
				if(big_flag == 0){
					result.hexadecimal[i] += this.hexadecimal[i];
				}
				else{
					result.hexadecimal[i] -= minuend.hexadecimal[i];
				}
			}
			if(result.hexadecimal[result.nums - 1] < 0){
				result = minuend.minus(this);
				result.flag = 1 - result.flag;
			}
			while(result.nums >= 1 && result.hexadecimal[result.nums - 1] == 0){
				result.nums--;
			}
			return result;
		}
		else{
			Huge_Number hn = new Huge_Number(minuend);
			hn.flag = 1 - hn.flag;
			return this.plus(hn);
		}
	}
	
	//operate *
	public Huge_Number multiple(Huge_Number multiplicand){
		Huge_Number result = new Huge_Number(this.nums + multiplicand.nums + 1);
		if((this.flag + multiplicand.flag) != 1){
			result.flag = 0;
		}
		else{
			result.flag = 1;
		}
		int offset = 0;
		int k;
		for(int i = 0; i < multiplicand.nums; i++){
			for(int j = 0; j < this.nums; j++){
				result.hexadecimal[j + offset] += multiplicand.hexadecimal[i] * this.hexadecimal[j];
				k = j + offset;
				while(result.hexadecimal[k] >= 16){
					result.hexadecimal[k + 1] += result.hexadecimal[k] / 16;
					result.hexadecimal[k] %= 16;
					k++;
				}
			}
			offset++;
		}
		while(result.nums > 0 && result.hexadecimal[result.nums - 1] == 0){
			result.nums--;
		}
		return result;
	}
	
	//operate /
	public Huge_Number divide(Huge_Number dividend){
		if(this.nums < dividend.nums){
			Huge_Number zero = new Huge_Number("0");
			return zero;
		}
		Huge_Number remaining = new Huge_Number(this);
		int offset = remaining.nums - dividend.nums;
		Huge_Number result =new Huge_Number(offset + 1);
		Huge_Number new_modulo = dividend.moveleft(offset);
		Huge_Number middle_result = remaining.minus(new_modulo);
		while(offset >= 0){
			while(middle_result.flag == 0){
				result.hexadecimal[offset]++;
				remaining = middle_result;
				middle_result = remaining.minus(new_modulo);
			}
			offset--;
			new_modulo = new_modulo.moveright(1);
			middle_result = remaining.minus(new_modulo);
		}
		while(result.nums >= 1 && result.hexadecimal[result.nums - 1] == 0){
			result.nums--;
		}
		return result;
	}
	
	//operate %
	public Huge_Number mod(Huge_Number modulo){
		if(this.nums < modulo.nums){
			Huge_Number this_number = new Huge_Number(this);
			return this_number;
		}
		Huge_Number result = new Huge_Number(this);
		int offset = result.nums - modulo.nums;
		Huge_Number new_modulo = modulo.moveleft(offset);
		Huge_Number middle_result = result.minus(new_modulo);
		while(offset >= 0){
			while(middle_result.flag == 0){
				result = middle_result;
				middle_result = result.minus(new_modulo);
			}
			offset--;
			new_modulo = new_modulo.moveright(1);
			middle_result = result.minus(new_modulo);
		}
		return result;
	}
	
	//operate <<
	public Huge_Number moveleft(int offset){
		Huge_Number result = new Huge_Number(this.nums + offset);
		for(int i = offset; i < result.nums; i++){
			result.hexadecimal[i] = this.hexadecimal[i - offset];
		}
		for(int i = 0; i < offset; i++){
			result.hexadecimal[i] = 0;	
		}
		return result;
	}
	
	//operate >>
	public Huge_Number moveright(int offset){
		if(this.nums <= offset){
			Huge_Number result = new Huge_Number(1);
			return result;
		}
		else{
			Huge_Number result = new Huge_Number(this.nums - offset);
			for(int i = 0; i < result.nums; i++){
				result.hexadecimal[i] = this.hexadecimal[i + offset];
			}
			return result;
		}
	}
	
	//operate ^
	public Huge_Number power(int exponent){
		Huge_Number result = new Huge_Number(this);
		for(int i = 1; i < exponent; i++){
			result = result.multiple(this);
		}
		return result;
	}
	
	//operate ^
	public Huge_Number power(Huge_Number exponent){
		Huge_Number result = new Huge_Number(this);
		Huge_Number flag = new Huge_Number("1");
		Huge_Number one = new Huge_Number("1");
		while(!flag.equal(exponent)){
			result = result.multiple(this);
			flag = flag.plus(one);
		}
		return result;
	}
	
	//operate ==
	public Boolean equal(Huge_Number another){
		if(this.nums != another.nums){
			return false;
		}
		else if (this.flag != another.flag){
			return false;
		}
		else{
			for(int i = 0; i < this.nums; i++){
				if(this.hexadecimal[i] != another.hexadecimal[i]){
					return false;
				}
			}
		}
		return true;
	}
	
	//operate >=
	public Boolean more_or_equal(Huge_Number another){
		if(this.flag == 1 && another.flag == 0){
			return false;
		}
		else if(this.flag == 0 && another.flag == 1){
			return true;
		}
		else if(this.flag == 0){
			if(this.nums > another.nums){
				return true;
			}
			else if(this.nums < another.nums){
				return false;
			}
			else{
				for(int i = this.nums - 1; i >= 0; i--){
					if(this.hexadecimal[i] > another.hexadecimal[i]){
						return true;
					}
					else if(another.hexadecimal[i] > this.hexadecimal[i]){
						return false;
					}
				}
			}
		}
		else{
			if(this.nums > another.nums){
				return false;
			}
			else if(this.nums < another.nums){
				return true;
			}
			else{
				for(int i = this.nums - 1; i >= 0; i--){
					if(this.hexadecimal[i] > another.hexadecimal[i]){
						return false;
					}
					else if(this.hexadecimal[i] > another.hexadecimal[i]){
						return true;
					}
				}
			}
		}
		return true;
	}
	
	//operate >
	public Boolean more_than(Huge_Number another){
		if(this.flag == 1 && another.flag == 0){
			return false;
		}
		else if(this.flag == 0 && another.flag == 1){
			return true;
		}
		else if(this.flag == 0){
			if(this.nums > another.nums){
				return true;
			}
			else if(this.nums < another.nums){
				return false;
			}
			else{
				for(int i = this.nums - 1; i >= 0; i--){
					if(this.hexadecimal[i] > another.hexadecimal[i]){
						return true;
					}
					else if(another.hexadecimal[i] > this.hexadecimal[i]){
						return false;
					}
				}
			}
		}
		else{
			if(this.nums > another.nums){
				return false;
			}
			else if(this.nums < another.nums){
				return true;
			}
			else{
				for(int i = this.nums - 1; i >= 0; i--){
					if(this.hexadecimal[i] > another.hexadecimal[i]){
						return false;
					}
					else if(this.hexadecimal[i] > another.hexadecimal[i]){
						return true;
					}
				}
			}
		}
		return false;
	}
	
	//operate >>1
	public Huge_Number rightMove(){
		Huge_Number result = this;
		for(int i = 0; i < result.nums - 1; i++){
			result.hexadecimal[i] = ((result.hexadecimal[i + 1] & 1)<< 3) | (result.hexadecimal[i]>>1);
		}
		result.hexadecimal[result.nums - 1] >>= 1;
		if(result.hexadecimal[result.nums - 1] == 0)
			result.nums--;
		return result;
	}
}
