pragma solidity ^0.4.4;
contract CheckContract {

    function check(string attributeA, string attributeB) pure public returns(bool){
		if(bytes(attributeA).length != bytes(attributeB).length){
			return false;
		}else{
			return keccak256(bytes(attributeA)) == keccak256(bytes(attributeA));
		}
    }
}