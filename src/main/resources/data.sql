insert into seller values(10001, 'pratik')
insert into seller values(10002, 'shru')
insert into seller values(10003, 'prats')

insert into project values(11001, 'configure blackhawk router as an FTP server', sysdate()+1, 11001, 0.5, 10001)
insert into project values(11002, 'assemble sofa-bed product id 123xyz', sysdate()+1, -1, 0, 10002)

insert into bidder values(12001, 'litesh')
insert into bidder values(12002, 'abhi')

insert into bid values(13001, sysdate(), 20.5, 12001, 11001)
insert into bid values(13002, sysdate(), 10.5, 12001, 11001)
insert into bid values(13003, sysdate(),  0.5, 12001, 11001)