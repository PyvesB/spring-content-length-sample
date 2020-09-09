# frozen_string_literal: true

require 'httparty'

read_io, write_io = IO.pipe
Thread.new do
  write_io << 'hello'
  write_io.close
end

begin
  HTTParty.post(
    'http://127.0.0.1:8080/data',
    # The connection will hang as the Content-Length was set to 100 but the Ruby client has no more data to send.
    # Specifying a timeout will terminate the connection.
    timeout: 1,
    max_retries: 0,
    body_stream: read_io,
    headers: { 'Content-Length' => '100' }
  )
rescue Net::ReadTimeout => e
end
