/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 *
 * @format
 * @flow
 */

import React, {Component} from 'react';
import {Platform, StyleSheet, Text, View} from 'react-native';
import { Button, TextInput } from 'react-native';
import {Alert, DeviceEventEmitter } from 'react-native';
import {NativeModules} from 'react-native';

const IntentServiceInterface = NativeModules.IntentServiceInterface;

const instructions = Platform.select({
  ios: 'Press Cmd+R to reload,\n' + 'Cmd+D or shake for dev menu',
  android:
    'Double tap R on your keyboard to reload,\n' +
    'Shake or press menu button for dev menu',
});

type Props = {};
export default class App extends Component<Props> {
	onSessionConnect = (event) => {
	  this.setState(previousState => {return { content: event };});	  
	}
	constructor(props) {
		super(props);
		this.state = {content: 'service inactive', input:''};
		DeviceEventEmitter.addListener('onSessionConnect', this.onSessionConnect);
	}
	
	startService =()=>{
		IntentServiceInterface.startService(this.state.input);   
	} 
	
  render() {
    return (
      <View style={styles.container}>
        <TextInput
          style={{height: 40}}
          placeholder="Hier Paramert für den Service eingeben"
          onChangeText={(text) => this.setState({input:text})}
        />
		<Button
			style={styles.buttons}
          onPress={this.startService}
          title="Start Service"         
          accessibilityLabel="Learn more about this purple button"
        />
		<Text style={styles.welcome}>State service:</Text>
        <Text >{this.state.content}</Text>
      </View>
    );
  }
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: '#F5FCFF',
  },
  welcome: {
    fontSize: 20,
    textAlign: 'center',
    margin: 10,
  },
  instructions: {
    textAlign: 'center',
    color: '#333333',
    marginBottom: 5,
  },
});
