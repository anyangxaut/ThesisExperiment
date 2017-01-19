function c = Covariance(X)
[n, k] = size(X);
Xc = X - repmat(mean(X), n, 1);
c = Xc' * Xc / n;