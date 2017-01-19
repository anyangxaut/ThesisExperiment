function [o] = MyCost(x)

	testdata_old = load('test.txt');
	reallabels = load('testlabels.txt');
	traindata_old = load('train.txt');
	trainlabels = load('trainlabels.txt');
	k = 3;
	error = 0;
	
	num = 1;
	column_index = x(1,:) == num;
	testdata = testdata_old(:,column_index);
	traindata = traindata_old(:,column_index);

	[testdata_row, testdata_col] = size(testdata);  
	predicted_label = zeros(testdata_row, 1);  

	for i = 1 : testdata_row  
		predicted_label(i) = knn(testdata(i,:), traindata, trainlabels, k);
	%	fprintf('predicted_label: %d  reallabels: %d\n',[predicted_label(i) reallabels(i)])
		if(predicted_label(i) ~= reallabels(i))
			error = error + 1;
		end
	end    
      
	testlabels = predicted_label;
	o = 1 - error/(testdata_row);
	fprintf('recognition rate: %f\n', o);

end

