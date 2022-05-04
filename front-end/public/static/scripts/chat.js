// Collapsible

var coll = document.getElementsByClassName("collapsible");

var popKeyPair = {
	key1: ["How orders matching works?", "Generally, order matching works when a buy order and a sell order are compatible if the maximum price of the buy order matches or exceeds the minimum price of the sell order.","popQn"], 
	
	key2: ["How do I join OMNI Trade?", "Register an account with us to join OMNI Trade, and you are well to kick start the order matching journey with us!","popQn"],
	
	key3: ["Why join OMNI Trade?", "Join OMNI Trade now to have superior market access, technology solutions, industry expertise!","popQn"],
	
	key4: ["How much commission does OMNI Trade collects?", "$0 commission! Exclusively for our members!","popQn"],
	
	key5: ["How to know if an order matching platform can be trusted?", "The order matching platform can be trusted if it is NASDAQ listed and is licensed by the Monetary Authority of Singapore (MAS)","hotTop"], 
	
	key6: ["What is the Stock Market?", "The stock market refers to public markets that exist for issuing, buying, and selling stocks that trade on a stock exchange or over-the-counter. Stocks, also known as equities, represent fractional ownership in a company, and the stock market is a place where investors can buy and sell ownership of such investible assets. An efficiently functioning stock market is considered critical to economic development, as it gives companies the ability to quickly access capital from the public.","hotTop"]
};

for (let i = 0; i < coll.length; i++) {
    coll[i].addEventListener("click", function () {
        this.classList.toggle("active");

        var content = this.nextElementSibling;

        if (content.style.maxHeight) {
            content.style.maxHeight = null;
        } else {
            content.style.maxHeight = content.scrollHeight + "px";
        }
    });
}

function getTime() {
    let today = new Date();
    hours = today.getHours();
    minutes = today.getMinutes();

    if (hours < 10) {
        hours = "0" + hours;
    }

    if (minutes < 10) {
        minutes = "0" + minutes;
    }

    let time = hours + ":" + minutes;
    return time;
}

// Gets the first message
function firstBotMessage() {
	
    let firstMessage = "Hi! I am here to guide you on your first step to OMNI Trading."
    
	document.getElementById("botStarterMessage").innerHTML = '<p class="botText"><span style="border-bottom: 0px solid black; padding-left: 15px">' + firstMessage + '</span></p>';
	
	let hotTopHTML = "<p class='botText'><span id='line' style='border-bottom: 0px solid black; padding-left: 15px'>Hot Topics"
	
	let popQnHTML = "<p class='botText'><span id='line' style='border-bottom: 0px solid black; padding-left: 15px'>Popular Questions"
	
	let spacingHTML = "<br><br>";
	
	hotTopHTML+=spacingHTML;
	
	popQnHTML+=spacingHTML;
	
	let keyCount = 0; 

	for (var key in popKeyPair) {
		++keyCount;
		if(popKeyPair[key][2]=="hotTop"){
		
			hotTopHTML += "<span class='fa fa-star' onclick='selectedQn("+keyCount+")'>&nbsp;&nbsp;<a href='#' style='text-decoration:none;'>" + popKeyPair[key][0] + "</a></span><br>";
		}else{
			popQnHTML += "<span class='fa fa-star' onclick='selectedQn("+keyCount+")'>&nbsp;&nbsp;<a href='#' style='text-decoration:none;'>" + popKeyPair[key][0] + "</a></span><br>";
			
		}
	}

	document.getElementById("botStarterMessage2").innerHTML = hotTopHTML+= "</span></p>";
	
    document.getElementById("botStarterMessage3").innerHTML = popQnHTML+= "</span></p>";
    
    document.getElementById("userInput").scrollIntoView(true);
}

firstBotMessage();

// Retrieves the response
function getHardResponse(userText) {
    let botResponse = getBotResponse(userText);
    let botHtml = '<p class="botText"><span>' + botResponse + '</span></p>';
    $("#chatbox").append(botHtml);

    document.getElementById("chat-bar-bottom").scrollIntoView(true);
}

//Gets the text text from the input box and processes it
function getResponse() {
    let userText = $("#textInput").val();

    if (userText == "") {  
		loader("Try asking me a question!");
		let dateTime = getDateTime()
		let userHtml = '<p class="userText"><br><span>' + userText + '</span></p><p class="userDateTimeText"> You at '+ dateTime +'</p>';
		$("#textInput").val("");
		$("#chatbox").append(userHtml);
		document.getElementById("chat-bar-bottom").scrollIntoView(true);
    }
	else{
		let dateTime = getDateTime()
		let userHtml = '<p class="userText"><span>' + userText + '</span></p><p class="userDateTimeText"> You at '+ dateTime +'</p>';
		let keyCount = 0;
	    let ansFound = false;
		for (var key in popKeyPair) {
			++keyCount;
			let question = popKeyPair[key][0];
			let answer = popKeyPair[key][1];
		
			let ansMatchResult = answer.match(userText);
				if (ansMatchResult!== null){
					loader(question,"header",keyCount);
					ansFound = true;
				}
				
			}
		if(ansFound==false){
			loader("Try entering another keyword!");
		}
		$("#textInput").val("");
		$("#chatbox").append(userHtml);
		document.getElementById("chat-bar-bottom").scrollIntoView(true);
	}
}

// Handles sending text via button clicks
function buttonSendText(sampleText) {
    let userHtml = '<p class="userText"><span>' + sampleText  + '</span></p>';
    $("#textInput").val("");
    $("#chatbox").append(userHtml);
    document.getElementById("chat-bar-bottom").scrollIntoView(true);
}

function sendButton() {
    getResponse();
}

function selectedQn(number) {
	let dateTime = getDateTime()
	let key = "key" + number;

    buttonSendText(popKeyPair[key][0] + '<p class="userDateTimeText"> You at '+ dateTime +'</p></p>')
	loader(popKeyPair[key][1]);
}

function loader(selectedQn, header,keyCount) {
	let dateTime = getDateTime()
	let headerHTML = "";
	let hrefOpenHTML = "";
	let hrefCloseHTML = "";
	let hrefHTML = "";
	let onclickOpenHTML = "";
	let spacingHTML = "";
	let headerValue = header;
	let spanCloseHTML = "";
	if (headerValue == "header"){
		headerHTML = "Related Questions<br><br>" 
		hrefOpenHTML = '<span class="fa fa-star"> <a href="#" style="text-decoration:none;">'
		hrefCloseHTML = '</a>'
		onclickOpenHTML = " id='line' onclick='selectedQn("+keyCount+")'"
		spanCloseHTML = '</span></p>'
		
	}
	let botHtml = '<p class="botText"><span style="border-bottom: 0px solid black; padding-left: 15px"'+onclickOpenHTML+'>' + headerHTML + hrefOpenHTML+ selectedQn + hrefCloseHTML+ '</span></p>'+spanCloseHTML+'<p class="botDateTimeText"><span>OMNI Trade at ' + dateTime +'</span></p>';
	let loader = '<p class="loader"><span></span></p>';
	
	$("#chatbox").append(loader);
	
	setTimeout(function(){
		$(".loader").remove();	
		$("#chatbox").append(botHtml);
	},750);
	
	document.getElementById("chat-bar-bottom").scrollIntoView(true);
}	

function getDateTime() {
	let time = getTime();
	
	var today = new Date();
	var dd = today.getDate();

	var months = ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"];
	var d = new Date();
	var monthName=months[d.getMonth()];

	if(dd<10) 
	{
		dd='0'+dd;
	} 

	today = dd + " " + monthName + ", " + time;
	
	return today;
	
}	

// Press enter to send a message
$("#textInput").keypress(function (e) {
    if (e.which == 13) {
        getResponse();
    }
});

//Related Questions
