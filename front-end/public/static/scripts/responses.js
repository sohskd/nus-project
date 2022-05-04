

function getBotResponse(input) {

	// Simple responses
    if (input.toUpperCase() == "T1") {
        return "Answer to T1";
    } else if (input.toUpperCase() == "T2") {
        return "Answer to T2";
    } else if (input.toUpperCase() == "P1") {
        return "Answer to P1";
    } else if (input.toUpperCase() == "P2") {
        return "Answer to P2";
    } else {
        return "Try asking something else!";
    }	
}