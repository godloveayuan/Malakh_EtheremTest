pragma solidity ^0.4.4;
contract PrivateCheckContract {

    function check(string attributeSub, string operate) pure public returns(bool){
		// hash 值比较，如果操作是 read
		if(keccak256(bytes(operate)) == keccak256("read")){
			if((keccak256(bytes(attributeSub))==keccak256("displayer"))
			|| (keccak256(bytes(attributeSub))==keccak256("controller"))){
				return true;
			}
		} else if(keccak256(bytes(operate)) == keccak256("control")){
			if((keccak256(bytes(attributeSub))==keccak256("controller"))){
				return true;
			}
		}

		return false;
    }
}