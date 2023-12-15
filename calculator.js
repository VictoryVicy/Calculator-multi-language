let result=document.getElementById("inputext");

let calculate=(number)=>{
    result.value+=number;
}

let Hasil=()=>{
    try {
        result.value=eval(result.value)
    }
    catch (err){
        alert("Please enter a valid value");
    }
}

function clr() {
    result.value = "";
}

function del() {
    result.value = result.value.slice(0, -1);
  }