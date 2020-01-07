/**
 *模块的路由类，被server的处理路由方法初始化和调用。然后这个类再调用真正的业务类方法
 */

function intellicyan(){
    this.name="my name is intellicyan!"
    this.setName=function(n){
        this.name=n;
    };
    this.sayHello=function(postParam){
     
    }
}
module.exports = intellicyan;

intellicyan.prototype.reg=function(req,res,postParam){
	var RegAndLogin=require("./regAndLogin/regAndLogin");
	var rg=new RegAndLogin();
	rg.reg(postParam);
	res.end("{'err':'0','err_msg':''}");//没错，成功
}
