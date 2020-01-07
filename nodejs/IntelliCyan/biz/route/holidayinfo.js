function holidayInfo(){
	this.collectionsName="holidayInfo";
}

holidayInfo.prototype.insert=function(req,res,postparam){
	var resString="okok!";
	console.log(postparam);
	//
	let Mongo=require('mongodb');
	var assert = require('assert');
	let url = 'mongodb://127.0.0.1:27017/mongo_db_base'
	let client=Mongo.MongoClient;
	var collectionsName=this.collectionsName;
	client.connect(url, function(err, db){
		assert.equal(null, err);
		//
		var count=0;
		var table=db.collection(collectionsName);
		table.find({"key":postparam.key}).limit(1).each(function(err, doc) {//其实这个处理有点问题 ，nodejs的mongo是必定会走doc==null的时候，
																			//也就是要在==null的时候处理所有数据,doc!=null的时候相当于是中间过程
																			//和读取post数据的处理感觉有点像
		  if(count<1){
			  console.log("count:"+count);
			  if(doc!=null) {
				console.log(doc.key+" is an exist holiday!");
				resString=doc.key+" is an exist holiday!";
				db.close();
				res.end( "{'err':'0','err_msg':"+resString+"}")
			  }else{
				console.log("prepare add a new holiday!");	
				table.insertOne({
					"year":postparam.year,
					"name":postparam.name,
					"key":postparam.key,
					"holidays":postparam.holidays,
					"workdays":postparam.workdays,
					"zindex":parseInt(postparam.zindex)
				},function(err,result){
					assert.equal(err, null);
					console.log("holidays insert ok.");
					resString=postparam.key+" holidays insert ok.";
					db.close();
					res.end( "{'err':'0','err_msg':"+resString+"}")
				});
			  }
			  count++;			 
		  }
		});
		//
	});
}
/*
holidayInfo.prototype.insertDetail=function(postparam){
	console.log(postparam);
}*/
holidayInfo.prototype.get=function(req,res,postparam){
	console.log(postparam);
	var resString="okok!";
	//
	let Mongo=require('mongodb');
	var assert = require('assert');
	let url = 'mongodb://127.0.0.1:27017/mongo_db_base'
	let client=Mongo.MongoClient;
	var collectionsName=this.collectionsName;
	client.connect(url, function(err, db){
		assert.equal(null, err);
		console.log("connected!")
		//
		var table=db.collection(collectionsName);
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
		//
	});
}
/*
holidayInfo.prototype.getDetail=function(postparam){
	console.log(postparam);
}*/
module.exports=holidayInfo;