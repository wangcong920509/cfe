package com.cfe.http.util;

import java.util.ArrayList;
import java.util.List;

import com.cfe.common.model.dto.CustomerDTO;
import com.cfe.common.model.dto.OrderDTO;
import com.cfe.common.model.dto.RestaurantDTO;

public class TestInfo {
	
	//TEST-FUNCTION-Start-----------------------------
			public List<OrderDTO> testOrders(){
				List<OrderDTO> ts = new ArrayList<OrderDTO>();
				
				OrderDTO t = new OrderDTO();
				t.setOid(123);
				t.setTime("2015-11-04");
				t.setPrice(49.0);
				
				RestaurantDTO r = new RestaurantDTO();
				r.setName("�ϵϼ����");
				r.setAddress("�廪��ѧ");
				r.setPhone("12345678910");
				t.setRid(r);
							
				CustomerDTO c = new CustomerDTO();
				c.setName("ѾѾ");
				c.setAddress("���ҹ�");
				c.setPhone("52013149780");
				t.setCid(c);
				
				ts.add(t);
				//-----------------------
				t = new OrderDTO();
				
				r = new RestaurantDTO();
				r.setName("�ϵϼ�3D���");
				r.setAddress("�廪��ѧ");
				r.setPhone("12345678910");
				t.setRid(r);
							
				c = new CustomerDTO();
				c.setName("�װ�");
				c.setAddress("�޲���");
				c.setPhone("52013149780");
				t.setCid(c);

				ts.add(t);
				return ts;
			}
			//TEST-FUNCTION-End-----------------------------

}
