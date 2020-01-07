
..省略以上代码
 
let oneToTen = [1,2,3,4,5,6,7,8,9,10];
 
// 错误示范
oneToTen.forEach(function (v) {
    console.log(`当前是第${v}次等待..`);
    await sleep(1000); // 错误!! await只能在async函数中运行
});
 
// 正确示范
for(var v of oneToTen) {
    console.log(`当前是第${v}次等待..`);
    await sleep(1000); // 正确, for循环的上下文还在async函数中
}