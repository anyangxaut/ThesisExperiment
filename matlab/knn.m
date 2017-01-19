function relustLabel = knn(inx, data, labels, k)

[datarow , datacol] = size(data);
diffMat = repmat(inx,[datarow,1]) - data ;
distanceMat = sqrt(sum(diffMat.^2,2));
[B , IX] = sort(distanceMat,'ascend');
len = min(k,length(B));
relustLabel = mode(labels(IX(1:len)));

end