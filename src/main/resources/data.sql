insert into seller values(10001, 'pratik')
insert into seller values(10002, 'shru')
insert into seller values(10003, 'prats')

insert into project values(11001, 'configure blackhawk router as an FTP server', sysdate()+1, 10001)
insert into project values(11002, 'assemble sofa-bed product id 123xyz', sysdate()+1, 10002)

insert into bidder values(12001, 'litesh')
insert into bidder values(12002, 'abhi')

insert into bid values(13001, sysdate(), 20.5, 12001, 11001)