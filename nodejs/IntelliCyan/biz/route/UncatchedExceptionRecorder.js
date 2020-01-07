function CollectUncatched(){
	this.collectionName="uex";
	this.colApp="colApp";//fmclock ttfh checkin lottery
	this.colDate="colDate";//yyyy-MM-dd
	this.colHour="colHour";//hour
	this.colExStr="exstr";//异常的信息
}

CollectUncatched.prototype._catch=function(req,res,postparam){
	let Mongo=require('mongodb');
	var assert = require('assert');
	let url = 'mongodb://127.0.0.1:27017/mongo_db_base'
	let client=Mongo.MongoClient;
	var collectionsName=this.collectionName;
	
	var _this=this;
	client.connect(url, function(err, db){
		assert.equal(null, err);
		console.log("connected!")
		//
		var table=db.collection(collectionsName);
		table.insertOne({
					"_app":postparam._app,
					"_date":postparam._date,
					"_hour":postparam._hour,
					"_exstr":postparam._exstr});
		res.end( "{'err':'0','err_msg':'okok'}");
		
		/*
		var cursor =table.find({"year":postparam.year}).sort({"zindex":1});
		
		var retJson={"err":"0","data":[]};
		cursor.each(function(err, doc) {//其实nodejs的查询是必定会走到doc==null的这里，所以在doc==null的时候做返回处理就好了
			assert.equal(err, null);
			if (doc != null) {
				//console.dir(doc);
				var one={"name":doc.name,"key":doc.key,"year":doc.year,"holidays":doc.holidays,"workdays":doc.workdays,"zindex":doc.zindex};
				retJson.data.push(one);
			} else {
				//callback();
				var retJsonStr=JSON.stringify(retJson);
				console.log(retJsonStr);
				res.end(retJsonStr);
			}
			db.close();
		});
		*/
		//
	});
}

module.exports=CollectUncatched;
