function d = MahalanobisDistance(A, B)
% Return mahalanobis distance of two data matrices 
% A and B (row = object, column = feature)
% @author: Kardi Teknomo
% http://people.revoledu.com/kardi/index.html
[n1, k1] = size(A);
[n2, k2] = size(B);
n = n1 + n2;
if(k1 ~= k2)
    disp('number of columns of A and B must be the same')
else
    xDiff = mean(A) - mean(B);       % mean difference row vector
    cA = Covariance(A);
    cB = Covariance(B);
    pC = (n1/n * cA) + (n2/n * cB);          % pooled covariance matrix
    d = sqrt(xDiff * inv(pC) * xDiff'); 	% mahalanobis distance
end