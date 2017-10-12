import tensorflow as tf

from tensorflow.python.framework.graph_util import convert_variables_to_constants

# Model parameters
W = tf.Variable([.3], dtype=tf.float32, name='weight')
b = tf.Variable([-.3], dtype=tf.float32, name='bias')
# Model input and output
x = tf.placeholder(tf.float32, name='input')
linear_model = tf.add(W * x , b, name='output')
y = tf.placeholder(tf.float32)

# loss
loss = tf.reduce_sum(tf.square(linear_model - y)) # sum of the squares
# optimizer
optimizer = tf.train.GradientDescentOptimizer(0.01)
train = optimizer.minimize(loss)

# training data
x_train = [1, 2, 3, 4]
y_train = [0, -1, -2, -3]
# training loop
init = tf.global_variables_initializer()
sess = tf.Session()
sess.run(init) # reset values to wrong
for i in range(1000):
  sess.run(train, {x: x_train, y: y_train})

output_graph_def = tf.graph_util.convert_variables_to_constants(sess, sess.graph_def, ['output'])
with tf.gfile.FastGFile('test.pb', mode = 'wb') as f:
  f.write(output_graph_def.SerializeToString())


# evaluate training accuracy
curr_W, curr_b, curr_loss = sess.run([W, b, loss], {x: x_train, y: y_train})
print("W: %s b: %s loss: %s"%(curr_W, curr_b, curr_loss))
