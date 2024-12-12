package services;

import java.io.File;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;

import Function.ApiCaller;

@Path("/api")
public class deployRuleAppService {

	public static Logger logger = Logger.getLogger(deployRuleAppService.class);

	@GET
	@Path("/getRuleApp/{projectName}/{SRNo}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getRuleApp(@PathParam("projectName") String name, @PathParam("SRNo") String srNo) throws Exception {
		String resultJSON = "";
		Model.Response response = null;
		try {
			resultJSON = ApiCaller.getRuleApp(name, srNo);
		} catch (Exception e) {
			response = new Model.Response("004", "取得RuleApp保存檔失敗！");
			logger.error("取得RuleApp保存檔失敗！");
			logger.error(e.getMessage(), e);
			ObjectMapper objectMapper = new ObjectMapper();
			resultJSON = objectMapper.writeValueAsString(response);
			File outputDirFile = new File("D:\\OdmTemp\\" + srNo);
			File[] outputFiles = outputDirFile.listFiles();
			for (File f : outputFiles) {
				f.delete();
			}
			return Response.status(Response.Status.OK).entity(resultJSON).build();
		}
		return Response.status(Response.Status.OK).entity(resultJSON).build();
	}

	@GET
	@Path("/deployRuleApp/UAT/{SRNo}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deployRuleApp_UAT(@PathParam("SRNo") String srNo) throws Exception {
		String resultJSON = "";
		try {
			resultJSON = ApiCaller.deployRuleApp("D:\\OdmTemp\\" + srNo, "UAT");
		} catch (Exception e) {
			ObjectMapper objectMapper = new ObjectMapper();
			Model.Response response = null;
			if (e.getClass().equals(NullPointerException.class)) {
				response = new Model.Response("002", "參數錯誤，請檢查 " + srNo + " 是否正確！");
				logger.error("參數錯誤，請檢查 " + srNo + " 是否正確！");
				logger.error(e.getMessage(), e);
			} else {
				response = new Model.Response("005", "部屬RuleApp保存檔至UAT失敗！");
				logger.error("部屬RuleApp保存檔至UAT失敗！");
				logger.error(e.getMessage(), e);
			}
			resultJSON = objectMapper.writeValueAsString(response);
		}
		return Response.status(Response.Status.OK).entity(resultJSON).build();
	}

	@GET
	@Path("/deployRuleApp/Prod/{SRNo}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deployRuleApp_Prod(@PathParam("SRNo") String srNo) throws Exception {
		String resultJSON = "";
		try {
			resultJSON = ApiCaller.deployRuleApp("D:\\OdmTemp\\" + srNo, "Prod");
		} catch (Exception e) {
			Model.Response response = null;
			if (e.getClass().equals(NullPointerException.class)) {
				response = new Model.Response("002", "參數錯誤，請檢查 " + srNo + " 是否正確！");
				logger.error("參數錯誤，請檢查 " + srNo + " 是否正確！");
				logger.error(e.getMessage(), e);
			} else {
				response = new Model.Response("005", "部屬RuleApp保存檔至Prod失敗！");
				logger.error("部屬RuleApp保存檔至Prod失敗！");
				logger.error(e.getMessage(), e);
			}
			ObjectMapper objectMapper = new ObjectMapper();
			resultJSON = objectMapper.writeValueAsString(response);
		}
		return Response.status(Response.Status.OK).entity(resultJSON).build();
	}

	@GET
	@Path("/deleteRuleApp/UAT/{projectName}/{SRNo}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteRuleApp_UAT(@PathParam("projectName") String name, @PathParam("SRNo") String srNo)
			throws Exception {
		String resultJSON = "";
		try {
			resultJSON = ApiCaller.deleteRuleApp_UAT(name, "D:\\OdmTemp\\" + srNo);
		} catch (Exception e) {
			Model.Response response = null;
			if (e.getClass().equals(NullPointerException.class)) {
				response = new Model.Response("007", "退版UAT成功，刪除檔案失敗，" + srNo + "錯誤");
				logger.error("退版UAT成功，刪除檔案失敗，" + srNo + "錯誤");
				logger.error(e.getMessage(), e);
			} else if (e.getClass().equals(IllegalArgumentException.class)) {
				response = new Model.Response("002", "參數錯誤，請檢查 " + name + "是否正確！");
				logger.error("參數錯誤，請檢查 " + name + "是否正確！");
				logger.error(e.getMessage(), e);
			} else {
				response = new Model.Response("006", "退版UAT失敗！");
				logger.error("退版UAT失敗！");
				logger.error(e.getMessage(), e);
			}
			ObjectMapper objectMapper = new ObjectMapper();
			resultJSON = objectMapper.writeValueAsString(response);
		}
		return Response.status(Response.Status.OK).entity(resultJSON).build();
	}

	@GET
	@Path("/deleteRuleApp/Prod/{projectName}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteRuleApp_Prod(@PathParam("projectName") String name, @PathParam("SRNo") String srNo)
			throws Exception {
		String resultJSON = "";
		try {
			resultJSON = ApiCaller.deleteRuleApp_Prod(name);
		} catch (Exception e) {
			Model.Response response = null;
			if (e.getClass().equals(IllegalArgumentException.class)) {
				response = new Model.Response("002", "參數錯誤，請檢查 " + name + "是否正確！");
				logger.error("參數錯誤，請檢查 " + name + "是否正確！");
				logger.error(e.getMessage(), e);
			} else {
				response = new Model.Response("006", "退版Prod失敗！");
				logger.error("退版Prod失敗！");
				logger.error(e.getMessage(), e);
			}
			ObjectMapper objectMapper = new ObjectMapper();
			resultJSON = objectMapper.writeValueAsString(response);
		}
		return Response.status(Response.Status.OK).entity(resultJSON).build();
	}
}
