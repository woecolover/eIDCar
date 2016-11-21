package trimps.eid.zl.rest;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import trimps.eid.zl.DAO.CarBatchDAO;
import trimps.eid.zl.DAO.CarInfoDAO;
import trimps.eid.zl.careid.EIDCodeService;
import trimps.eid.zl.entity.CarBatch;
import trimps.eid.zl.entity.CarInfo;
import trimps.eid.zl.enums.CarState;
import trimps.eid.zl.enums.PersonState;
import trimps.eid.zl.enums.ResultState;
import trimps.eid.zl.util.JsonUtil;

/**
 * Servlet implementation class Register
 */
@WebServlet("/rest/car/creatBatch")
public class CarCreateBatch extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Inject
	Logger log;

	@EJB
	private CarInfoDAO carInfoDAO;

	@EJB
	private CarBatchDAO carBatchDAO;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CarCreateBatch() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.info("come in CarCreateBatch ... ... ");
		String resultStr = "";
		JSONObject resultJson = new JSONObject();
		String paramJsonStr = JsonUtil.convertStramToString(request.getInputStream());
		log.info("paramJsonStr : " + paramJsonStr);
		JSONObject paramJsons = JSONObject.fromObject(paramJsonStr);
		String batch_num = paramJsons.getString("batch_num");
		String batch_count_str = paramJsons.getString("batch_count");
		int batch_count = Integer.valueOf(batch_count_str).intValue();
		JSONArray car_list = paramJsons.getJSONArray("batch_list");
		CarBatch cb = new CarBatch();
		cb.setBatch_num(batch_num);
		cb.setBatch_count(batch_count);
		cb.setBatch_time(new Date());
		carBatchDAO.save(cb);

		JSONArray batch_result_json = new JSONArray();
		for (int i = 0; i < batch_count; i++) {
			JSONObject car = car_list.getJSONObject(i);
			String car_num = car.getString("car_num");
			String owner_name = car.getString("owner_name");
			String owner_num = car.getString("owner_num");
			String rfid_num = car.getString("rfid_num");

			JSONObject car_result = new JSONObject();
			List<CarInfo> objs = carInfoDAO.getByCondition("rfid_num", rfid_num);
			if (objs.size() > 0) {
				car_result.put("result_code", ResultState.Fail.getState());
				car_result.put("reason", "Created(已注册)");
				car_result.put("rfid_num", rfid_num);
				batch_result_json.add(car_result);
				continue;
			}

			String car_eid = "";
			try {
				car_eid = EIDCodeService.eIDCodeOfCar(car_num, owner_name, owner_num, rfid_num);
			} catch (Exception e) {
				e.printStackTrace();
				car_result.put("result_code", ResultState.Fail.getState());
				car_result.put("reason", "GenerateCar_eidError(car_eid生成错误)");
				car_result.put("rfid_num", rfid_num);
				batch_result_json.add(car_result);
				continue;
			}

			CarInfo carInfo = new CarInfo();
			carInfo.setCar_num(car_num);
			carInfo.setOwner_name(owner_name);
			carInfo.setOwner_num(owner_num);
			carInfo.setRfid_num(rfid_num);
			carInfo.setBatch_num(batch_num);
			carInfo.setPerson_state(PersonState.Normal.getState());
			carInfo.setPerson_state_time(new Date());
			carInfo.setCar_state(CarState.Create.getState());
			carInfo.setCar_state_time(new Date());
			carInfo.setCar_eid(car_eid);
			carInfoDAO.save(carInfo);

			car_result.put("result_code", ResultState.Success.getState());
			car_result.put("car_eid", car_eid);
			car_result.put("rfid_num", rfid_num);
			batch_result_json.add(car_result);
			continue;
		}

		resultJson.put("result_code", ResultState.Success.getState());
		resultJson.put("batch_result", batch_result_json);
		resultStr = resultJson.toString();
		log.info("resultStr : " + resultStr);
		response.getOutputStream().write(resultStr.getBytes());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
		this.doGet(request, response);
	}

}
