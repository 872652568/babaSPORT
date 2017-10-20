package cn.babasport.controller.sku;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.babasport.pojo.product.Sku;
import cn.babasport.service.sku.SkuService;

@Controller
@RequestMapping("/sku")
public class SkuController {

	@Resource
	private SkuService skuService;

	@RequestMapping("/list.do")
	public String list(Long productId, Model model) {
		List<Sku> skus = skuService.selectSkuByProductId(productId);
		model.addAttribute("skus", skus);
		return "sku/list";
	}
	
	@RequestMapping("/updateSku.do")
	public void updateSku(Sku sku,HttpServletResponse response) throws Exception{
		skuService.updateSku(sku);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("msg", "操作成功!");
		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().write(jsonObject.toString());
		
	}
}
