function RegAndLogin(){  
}
RegAndLogin.prototype.reg=function(postParam){
	//var json=JSON.parse(postParam);//JSON.stringify(JSONOBJ);
	//console.log(postParam);
	console.log("RegAndLogin.reg,name="+postParam.name);
	console.log("RegAndLogin.reg,pwd="+postParam.pwd);
	//console.log("RegAndLogin.reg,sign="+postParam.sign);
	let Mongo=require('mongodb');
	var assert = require('assert');
	let url = 'mongodb://127.0.0.1:27017/mongo_db_base'
	let client=Mongo.MongoClient;
	client.connect(url, function(err, db){
		assert.equal(null, err);
		console.log("Connected successfully to server");
		//
		let count=0;
		var table=db.collection("intellicyan_user");
		table.find({"login_name":postParam.name}).limit(1).each(function(err, doc) {
			if(doc) {
				count++;		
			}else{
				if(count==1){
					console.log(postParam.name+" is an exist user! *");	
				}else{
					console.log(postParam.name+" prepare add a new user! *");	
				}
				//
				db.close();
			}
		});
		
	});
}
module.exports = RegAndLogin;