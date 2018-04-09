var globalColorsLoopArray= [
    '#058DC7', '#50B432', '#ED561B', '#24CBE5', '#CE0000',
    '#64E572', '#FF9655', '#FFF263', '#6AF9C4', '#EC2500',
    '#7cb5ec', '#434348', '#90ed7d', '#f7a35c', '#8085e9',
    '#f15c80', '#e4d354', '#8085e8', '#8d4653', '#91e8e1',
    '#ECE100', '#EC9800', '#9EDE00', '#DDDF00', '#c23531',
    '#2f4554', '#61a0a8', '#d48265', '#91c7ae', '#749f83',
    '#ca8622', '#bda29a', '#6e7074', '#546570', '#c4ccd3'
];

function getCurrentGlobalColor(colorIndex){
    if(colorIndex<globalColorsLoopArray.length){
        return globalColorsLoopArray[colorIndex];
    }else{
        return globalColorsLoopArray[colorIndex%globalColorsLoopArray.length];
    }
}

function getQueryString(name) {
    var reg = new RegExp('(^|&)' + name + '=([^&]*)(&|$)', 'i');
    var r = window.location.search.substr(1).match(reg);
    if (r != null) {
        return unescape(r[2]);
    }
    return null;
}

function getMeasurablePropertyNumberValue(measurableValue,propertyName){
    var measurableProperties=measurableValue.measurableProperties;
    var currentPropertyValue;
    for(var i=0;i<measurableProperties.length;i++){
        currentPropertyValue=measurableProperties[i];
        if(currentPropertyValue.propertyName==propertyName){
            return Number(currentPropertyValue.propertyValue);
        }
    }
    return 0;
}

function getMeasurablePropertyOriginalValue(measurableValue,propertyName){
    var measurableProperties=measurableValue.measurableProperties;
    var currentPropertyValue;
    for(var i=0;i<measurableProperties.length;i++){
        currentPropertyValue=measurableProperties[i];
        if(currentPropertyValue.propertyName==propertyName){
            return currentPropertyValue.propertyValue;
        }
    }
    return "";
}

/* this logic has a defect, all English will changed to lowercase
function getCharFromUtf8(str) {
    var cstr = "";
    var nOffset = 0;
    if (str == "")
        return "";
    str = str.toLowerCase();
    nOffset = str.indexOf("%e");
    if (nOffset == -1)
        return str;
    while (nOffset != -1) {
        cstr += str.substr(0, nOffset);
        str = str.substr(nOffset, str.length - nOffset);
        if (str == "" || str.length < 9)
            return cstr;
        cstr += utf8ToChar(str.substr(0, 9));
        str = str.substr(9, str.length - 9);
        nOffset = str.indexOf("%e");
    }
    return cstr + str;
}
*/

function getCharFromUtf8(str) {
    var cstr = "";
    var nOffset = 0;
    if (str == "")
        return "";
    nOffset = str.indexOf("%E");
    if (nOffset == -1)
        return str;
    while (nOffset != -1) {
        cstr += str.substr(0, nOffset);
        str = str.substr(nOffset, str.length - nOffset);
        if (str == "" || str.length < 9)
            return cstr;
        cstr += utf8ToChar(str.substr(0, 9));
        str = str.substr(9, str.length - 9);
        nOffset = str.indexOf("%E");
    }
    return cstr + str;
}

function utf8ToChar(str) {
    var iCode, iCode1, iCode2;
    iCode = parseInt("0x" + str.substr(1, 2));
    iCode1 = parseInt("0x" + str.substr(4, 2));
    iCode2 = parseInt("0x" + str.substr(7, 2));
    return String.fromCharCode(((iCode & 0x0F) << 12) | ((iCode1 & 0x3F) << 6) | (iCode2 & 0x3F));
}

function getUTF8UrlParam(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
    var r = window.location.search.substr(1).match(reg); //匹配目标参数
    if(r != null) return unescape(getCharFromUtf8(r[2])); //增加UTF-8解码处理。
    return null; //返回参数值
}