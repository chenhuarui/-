package action;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import entity.UserEntity;
import net.sf.json.JSONArray;
import service.UserService;

import java.util.ArrayList;
import java.util.List;

/**
 * 登录Action
 * 判断用户名和密码是否符合
 */
public class UserAction extends ActionSupport implements ModelDriven {

    private UserService userService;

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    private UserEntity user = new UserEntity();

    private JSONArray jsonArray;

    public JSONArray getJsonArray() {
        return jsonArray;
    }

    public void setJsonArray(JSONArray jsonArray) {
        this.jsonArray = jsonArray;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    @Override
    public Object getModel() {
        return this.user;
    }

    @Override
    public String execute() {

        if(user.getUserName().equals("") || user.getUserPassword().equals(""))
            return "error";

        //根据用户名和密码->搜索数据库：判断数据库是否存在该用户
        //保存该用户的id(唯一标识)
        //不存在则:停留登录界面 并 返回提示信息

        //获取ActionContext
        ActionContext actionContext = ActionContext.getContext();

        if( (user = userService.isExistsUser(user.getUserName(), user.getUserPassword())) != null) {

            //将用户存储在session
            actionContext.getSession().put("user", user);

            return "success";
        }else {
            return "error";
        }
    }

    //返回对应部门的人-下拉框
    public String getPerson() {

        List<UserEntity> list = userService.getPerson(user.getDepartment());
        List<String> names = new ArrayList<>();

        for(UserEntity u : list) {
            names.add(u.getUserName());
        }

        jsonArray = JSONArray.fromObject(names);
        return "json";
    }


}
