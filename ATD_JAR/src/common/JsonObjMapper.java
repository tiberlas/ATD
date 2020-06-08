package common;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.google.gson.Gson;

@Stateless
@LocalBean
public class JsonObjMapper {

	public String objToJson(Object obj) {
		Gson gson = new Gson();
        return gson.toJson(obj);
	}
	
	@SuppressWarnings("unchecked")
	public Object JsonToObj(String json, @SuppressWarnings("rawtypes") Class cl) {
		Gson gson = new Gson();
        return gson.fromJson(json, cl);
	}
}
