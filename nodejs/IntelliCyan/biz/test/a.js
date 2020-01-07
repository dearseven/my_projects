function a(){
    this.name="my name is a!"
    this.setName=function(n){
        this.name=n;
    };
    this.sayHello=function(req,res){
        res.write("\nhi,"+this.name);
        console.log("\nhi,"+this.name);
    }
}
module.exports = a;
